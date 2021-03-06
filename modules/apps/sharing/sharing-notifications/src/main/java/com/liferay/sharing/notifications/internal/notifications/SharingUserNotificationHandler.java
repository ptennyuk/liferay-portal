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

package com.liferay.sharing.notifications.internal.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.constants.SharingPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SharingPortletKeys.SHARING,
	service = UserNotificationHandler.class
)
public class SharingUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public SharingUserNotificationHandler() {
		setPortletId(SharingPortletKeys.SHARING);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject userNotificationEventPayloadJSONObject =
			JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());

		String message = userNotificationEventPayloadJSONObject.getString(
			"message");

		if (Validator.isNull(message)) {
			_userNotificationEventLocalService.deleteUserNotificationEvent(
				userNotificationEvent);
		}

		return message;
	}

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}