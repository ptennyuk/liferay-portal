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

package com.liferay.portal.search.test.util.count;

import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
public abstract class BaseCountTestCase extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		for (int i = 0; i < _TOTAL_DOCUMENTS; i++) {
			addDocument(
				DocumentCreationHelpers.singleText(
					"test-field",
					RandomTestUtil.randomString(
						UniqueStringRandomizerBumper.INSTANCE)));
		}
	}

	@Test
	public void testAll() throws Exception {
		_assertCount(createSearchContext());
	}

	@Test
	public void testPaginationIsIgnored() throws Exception {
		SearchContext searchContext = createSearchContext();

		int start = 5;

		searchContext.setEnd(start - 1);
		searchContext.setStart(start);

		_assertCount(searchContext);
	}

	private void _assertCount(SearchContext searchContext) throws Exception {
		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				IndexSearcher indexSearcher = getIndexSearcher();

				long count = indexSearcher.searchCount(
					searchContext, getDefaultQuery());

				Assert.assertEquals(_TOTAL_DOCUMENTS, count);

				return null;
			});
	}

	private static final int _TOTAL_DOCUMENTS = 20;

}