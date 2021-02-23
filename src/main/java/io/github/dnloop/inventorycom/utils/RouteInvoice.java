package io.github.dnloop.inventorycom.utils;

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
