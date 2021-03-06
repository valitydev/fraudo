package dev.vality.fraudo.resolver;


import dev.vality.fraudo.model.Pair;

public interface FieldResolver<T, U> {

    String resolveValue(String fieldName, T model);

    U resolveName(String fieldName);

    default Pair<U, String> resolve(String fieldName, T model) {
        return new Pair<>(
                resolveName(fieldName),
                resolveValue(fieldName, model)
        );
    }

}
