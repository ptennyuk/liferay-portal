/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.structured.content.apio.architect.sort;

import java.util.List;

/**
 * Models a Parser from String to Sort Keys.
 *
 * @author Cristina González
 */
public interface SortParser {

	/**
	 * Returns a List of {@link Sort.SortKey} obtained from a String.
	 *
	 * @param  sortExpressions - String to be parsed
	 * @return a  List of  {@link Sort.SortKey}
	 * @review
	 */
	public List<Sort.SortKey> parse(String sortExpressions);

}