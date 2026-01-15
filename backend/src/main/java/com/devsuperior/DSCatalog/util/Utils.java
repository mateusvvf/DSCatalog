package com.devsuperior.DSCatalog.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.devsuperior.DSCatalog.projections.IdProjection;

public class Utils {

	public static <ID, T extends IdProjection<ID>, U extends IdProjection<ID>> List<U> replace(
	        final Collection<T> ordered,
	        final Collection<U> unordered) {
	    final Map<ID, U> map = unordered.stream().collect(Collectors.toMap(U::getId, Function.identity()));
	    final Function<T, U> fromUnorderedById = t -> map.get(t.getId());
	    return ordered.stream().map(fromUnorderedById).toList();
	}

	
}
