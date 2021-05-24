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
