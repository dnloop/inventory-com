/*
 *     Inventory-Com: Inventory and Commerce Management Application.
 *     Copyright (C) 2021. dnloop.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.github.dnloop.inventorycom.support.uiloader;

public enum RouteInvoice implements PathInvoice {
	BUYS {

		@Override
		public String editInvoice() {
			return "/fxml/purchase/editInvoice.fxml";
		}

		@Override
		public String editDetails() {
			return "/fxml/purchase/editDetails.fxml";
		}

		@Override
		public String display() {
			return "/fxml/purchase/invoice.fxml";
		}

	},

	SALES {

		@Override
		public String editInvoice() {
			return "/fxml/sales/editInvoice.fxml";
		}

		@Override
		public String editDetails() {
			return "/fxml/sales/editDetails.fxml";
		}

		@Override
		public String display() {
			return "/fxml/sales/invoice.fxml";
		}
	}
}
