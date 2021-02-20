package io.github.dnloop.inventorycom.utils;

public enum RouteInvoice implements PathInvoice {
	BUYS {

		@Override
		public String editInvoice() {
			return "/fxml/buys/editInvoice.fxml";
		}

		@Override
		public String editDetails() {
			return "/fxml/buys/editDetails.fxml";
		}

		@Override
		public String display() {
			return "/fxml/buys/invoice.fxml";
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
