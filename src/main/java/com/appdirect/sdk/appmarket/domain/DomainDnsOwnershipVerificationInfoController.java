/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.domain;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DomainDnsOwnershipVerificationInfoController {

	private final DomainDnVerificationInfoHandler handler;

	DomainDnsOwnershipVerificationInfoController(DomainDnVerificationInfoHandler handler) {
		this.handler = handler;
	}

	@RequestMapping(
			method = GET,
			value = "/api/v1/integration/customers/{customerIdentifier}/domains/{domain}/ownershipProofRecord",
			produces = APPLICATION_JSON_VALUE
	)
	public DnsOwnershipVerificationRecords readOwnershipVerificationRecord(@PathVariable("customerIdentifier") String customerId,
																		   @PathVariable("domain") String domain) {

		return handler.readOwnershipVerificationRecords(customerId, domain);
	}
}
