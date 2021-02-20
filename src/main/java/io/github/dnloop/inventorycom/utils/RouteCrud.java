package io.github.dnloop.inventorycom.utils;

/**
 * <p>
 * Displays the constant paths of the FXML files used as views in the project.
 * The unimplemented methods throws UnsupportedOperationException.
 * </p>
 *
 * @author dnloop
 */
public enum RouteCrud implements PathCrud {

	CLIENT {
		@Override
		public String edit() {
			return "/fxml/client/edit.fxml";
		}

		@Override
		public String create() {
			return "/fxml/client/new.fxml";
		}

		@Override
		public String display() {
			return "/fxml/client/view.fxml";
		}

		@Override
		public String recover() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Not supported for this method.");
		}

		@Override
		public String extra() {
			return "/fxml/client/tel.fxml";
		}

	},

	PRODUCT {

		@Override
		public String edit() {
			return "/fxml/product/edit.fxml";
		}

		@Override
		public String create() {
			return "/fxml/product/new.fxml";
		}

		@Override
		public String display() {
			return "/fxml/product/view.fxml";
		}

		@Override
		public String recover() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Not supported for this method.");
		}

		@Override
		public String extra() {
			return "/fxml/product/category.fxml";
		}
	},

	SUPPLIER {

		@Override
		public String edit() {
			return "/fxml/supplier/edit.fxml";
		}

		@Override
		public String create() {
			return "/fxml/supplier/new.fxml";
		}

		@Override
		public String display() {
			return "/fxml/supplier/view.fxml";
		}

		@Override
		public String recover() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Not supported for this method.");
		}

		@Override
		public String extra() {
			return "/fxml/supplier/tel.fxml";
		}

	}
}
