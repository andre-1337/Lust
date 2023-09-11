package com.andre1337.lust.parser;

import com.andre1337.lust.parser.token.Token;

import java.lang.foreign.Linker;
import java.util.*;
import java.util.stream.*;

public class Type {
	public static final String TYPE_UNINIT = "uninit";
	public static final String TYPE_ANY = "any";
	public static final String TYPE_BOOL = "bool";
	public static final String TYPE_FN = "fn";
	public static final String TYPE_NUM = "num";
	public static final String TYPE_INT = "int";
	public static final String TYPE_FLOAT = "float";
	public static final String TYPE_STR = "str";
	public static final String TYPE_NULL = "null";
	public static final String TYPE_VEC = "Vec";
	public static final String TYPE_MAP = "Map";
	public static final String TYPE_STRUCT = "struct";
	public static final String TYPE_TRAIT = "trait";
	public static final String TYPE_ENUM = "enum";
	public static final String TYPE_TYPE = "type";

	public static class Generics {
		public List<ValueType> types;

		public Generics(List<ValueType> types) {
			this.types = types;
		}

		public List<ValueType> getTypes() {
			return this.types;
		}
	}

	public static class FnType {
		public static String TYPE = TYPE_FN;
		public List<ValueType> parameterTypes;
		public ValueType returnType;

		public FnType(List<ValueType> parameterTypes, ValueType returnType) {
			this.parameterTypes = parameterTypes;
			this.returnType = returnType;
		}

		public static String constructType(List<ValueType> parameterTypes, ValueType returnType) {
			return String.format(
					"%s(%s)%s",
					FnType.TYPE,
					parameterTypes
							.stream()
							.map(ValueType::toString)
							.collect(Collectors.joining(", ")),
					returnType != ValueType.Null ? ": " + returnType.toString() : ""
			);
		}

		public List<ValueType> getParameterTypes() {
			return this.parameterTypes;
		}

		public ValueType getReturnType() {
			return this.returnType;
		}
	}
	public enum ValueType {
		Uninit,
		Num,
		Int,
		Float,
		Bool,
		Null,
		Str,
		Vec,
		Map,
		Fn,
		Instance,
		Any,
		Union
	}

	private final ValueType valueType;
	private Generics generics;
	private FnType fnType;
	private String instance;
	private List<ValueType> union;

	public Type(ValueType valueType) {
		this.valueType = valueType;
	}

	public Type(ValueType valueType, Generics generics) {
		this.valueType = valueType;
		this.generics = generics;
	}

	public Type(ValueType valueType, FnType fnType) {
		this.valueType = valueType;
		this.fnType = fnType;
	}

	public Type(String instance) {
		this.valueType = ValueType.Instance;
		this.instance = instance;
	}

	public Type(List<ValueType> union) {
		this.valueType = ValueType.Union;
		this.union = union;
	}

	public static Type newVec(List<ValueType> generics) {
		if (generics == null || generics.isEmpty()) {
			generics = List.of(ValueType.Null);
		}

		return new Type(ValueType.Vec, new Generics(generics));
	}
}
