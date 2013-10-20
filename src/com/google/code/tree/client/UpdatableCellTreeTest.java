package com.google.code.tree.client;

/* Copyright 2013 Grant Slender
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * */

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

public class UpdatableCellTreeTest implements EntryPoint {

	private int count = 0;

	public void onModuleLoad() {
		final SingleSelectionModel<UpdatableTreeNode> selectionModelCellTree = new SingleSelectionModel<UpdatableTreeNode>();
		final UpdatableTreeModel treeModel = new UpdatableTreeModel(selectionModelCellTree);

		CellTree cellTree = new CellTree(treeModel, null);
		treeModel.setCellTree(cellTree);
		cellTree.setSize("100%", "360px");

		Button btnAdd = new Button("Add");
		btnAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				String label = "TestLabel";
				int cnt = count;
				count++;

				UpdatableTreeNode parent = selectionModelCellTree.getSelectedObject();
				if (parent != null) {
					label = parent.getLabel();
					cnt = parent.getDataProvider().getList().size() + 1;
				}
				UpdatableTreeNode child = new AbstractUpdatableTreeNode(label + cnt) {
				};
				treeModel.add(parent, child);
			}
		});

		Button btnRemove = new Button("Remove");
		btnRemove.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				UpdatableTreeNode node = selectionModelCellTree.getSelectedObject();
				if (node != null)
					treeModel.remove(node);
			}
		});

		final VerticalPanel panel = new VerticalPanel();
		panel.setBorderWidth(1);
		panel.setWidth("600px");
		panel.setHeight("400px");
		panel.add(cellTree);
		panel.add(btnAdd);
		panel.add(btnRemove);
		RootPanel.get("gwt").add(panel);

	}
}
