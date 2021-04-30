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

package io.github.dnloop.inventorycom.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utility wrapper class used to create a Pageable property used in a repository.
 */
public class PageableProperty {

    private final Pageable pageable;

    private final Pageable pageableDeleted;

    public PageableProperty() {
        pageable = PageRequest.of(
                0, 50,
                Sort.by("description").ascending()
        );

        pageableDeleted = PageRequest.of(
                0, 50,
                Sort.by("description").ascending()
                    .and(Sort.by("deletedAt").ascending())
        );
    }

    public PageableProperty(String description) {
        int max = 50;
        int min = 0;
        pageable = PageRequest.of(
                min, max,
                Sort.by(description).ascending()
        );

        pageableDeleted = PageRequest.of(
                min, max,
                Sort.by(description).ascending()
                    .and(Sort.by("deletedAt").ascending())
        );
    }

    public PageableProperty(String description, int max, int min) {

        pageable = PageRequest.of(
                min, max,
                Sort.by(description).ascending()
        );

        pageableDeleted = PageRequest.of(
                min, max,
                Sort.by(description).ascending()
                    .and(Sort.by("deletedAt").ascending())
        );
    }

    public Pageable getPageable() {
        return pageable;
    }

    public Pageable getPageableDeleted() {
        return pageableDeleted;
    }
}