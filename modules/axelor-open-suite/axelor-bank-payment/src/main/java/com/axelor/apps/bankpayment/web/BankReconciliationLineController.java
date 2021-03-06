/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2022 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.apps.bankpayment.web;

import com.axelor.apps.bankpayment.db.BankReconciliation;
import com.axelor.apps.bankpayment.service.bankreconciliation.BankReconciliationService;
import com.axelor.exception.service.TraceBackService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

public class BankReconciliationLineController {

  public void setAccountDomain(ActionRequest request, ActionResponse response) {

    try {
      Context parentContext = request.getContext().getParent();
      BankReconciliation bankReconciliation = null;
      if (parentContext != null
          && parentContext
              .getContextClass()
              .toString()
              .equals(BankReconciliation.class.toString())) {
        bankReconciliation = parentContext.asType(BankReconciliation.class);
      } else if (parentContext == null
          || !parentContext
              .getContextClass()
              .toString()
              .equals(BankReconciliation.class.toString())) {
        bankReconciliation = (BankReconciliation) request.getContext().get("bankReconciliation");
      }
      String domain =
          Beans.get(BankReconciliationService.class).getAccountDomain(bankReconciliation);
      response.setAttr("account", "domain", domain);
    } catch (Exception e) {
      TraceBackService.trace(response, e);
    }
  }
}
