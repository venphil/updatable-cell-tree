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

import java.util.ArrayList;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class UpdatableTreeModel implements TreeViewModel {

	ValueUpdater<UpdatableTreeNode> valueUpdater = null;
	SingleSelectionModel<UpdatableTreeNode> selectionModelCellTree = null;
	ListDataProvider<UpdatableTreeNode> rootDataProvider = null;
	int inputBoxSize;
	CellTree tree;

	public UpdatableTreeModel(SingleSelectionModel<UpdatableTreeNode> selectionModelCellTree) {
		this(selectionModelCellTree, null, 20);
	}

	public UpdatableTreeModel(SingleSelectionModel<UpdatableTreeNode> selectionModelCellTree, ValueUpdater<UpdatableTreeNode> valueUpdater) {
		this(selectionModelCellTree, valueUpdater, 20);
	}

	public UpdatableTreeModel(SingleSelectionModel<UpdatableTreeNode> selectionModelCellTree, ValueUpdater<UpdatableTreeNode> valueUpdater, int inputBoxSize) {
		this.selectionModelCellTree = selectionModelCellTree;
		this.valueUpdater = valueUpdater;
		this.inputBoxSize = inputBoxSize;
		this.rootDataProvider = new ListDataProvider<UpdatableTreeNode>(new ArrayList<UpdatableTreeNode>());
	}

	public ListDataProvider<UpdatableTreeNode> getRootDataProvider() {
		return rootDataProvider;
	}

	public void add(UpdatableTreeNode parent, UpdatableTreeNode child) {

		if (parent == null) {
			// root-node
			rootDataProvider.getList().add(child);
		} else {
			parent.addChild(child);
			if (parent.getParent() == null) {
				rootDataProvider.refresh();
			} else {
				parent.getParent().getDataProvider().refresh();
			}
		}
	}

	public class NodeChildToClose {
		public TreeNode node;
		public int childIndex;
	}

	private NodeChildToClose searchTreeNode(TreeNode parent, UpdatableTreeNode nodeToCheck) {
		int childCount = parent.getChildCount();
		for (int idx = 0; idx < childCount; idx++) {
			boolean leaveOpen = parent.isChildOpen(idx);
			TreeNode node = parent.setChildOpen(idx, true, false);
			if (!leaveOpen)
				parent.setChildOpen(idx, false, false);
			if (node != null) {
				UpdatableTreeNode utn = (UpdatableTreeNode) node.getValue();
				NodeChildToClose nctc = null;
				if (nodeToCheck.getParent() == utn) {
					nctc = new NodeChildToClose();
					nctc.node = parent;
					nctc.childIndex = idx;
					return nctc;
				} else {
					if (node.getChildCount() > 0) {
						nctc = searchTreeNode(node, nodeToCheck);
						if (nctc != null)
							return nctc;
					}
				}
			}
		}
		return null;
	}

	public void remove(UpdatableTreeNode objToRemove) {
		UpdatableTreeNode parent = objToRemove.getParent();

		if (parent == null) {
			// root-node
			rootDataProvider.getList().remove(objToRemove);
		} else {
			// find open node and close it when last child is to be removed !!!
			if (objToRemove.getParent().getChildCount() == 1) {
				NodeChildToClose nctc = searchTreeNode(tree.getRootTreeNode(), objToRemove);
				if (nctc != null)
					nctc.node.setChildOpen(nctc.childIndex, false);
			}
			parent.removeChild(objToRemove);
			if (parent.getParent() == null) {
				rootDataProvider.refresh();
			} else {
				parent.getParent().getDataProvider().refresh();
			}
		}
		selectionModelCellTree.clear();
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		Cell<UpdatableTreeNode> cell = new CustomEditTextCell(inputBoxSize);
		if (value == null) { // root is not set
			return new DefaultNodeInfo<UpdatableTreeNode>(rootDataProvider, cell, selectionModelCellTree, valueUpdater);
		} else {
			return new DefaultNodeInfo<UpdatableTreeNode>(((UpdatableTreeNode) value).getDataProvider(), cell, selectionModelCellTree, valueUpdater);
		}
	}

	@Override
	public boolean isLeaf(Object value) {
		if (value instanceof UpdatableTreeNode) {
			UpdatableTreeNode t = (UpdatableTreeNode) value;
			if (!t.hasChildren())
				return true;
			return false;
		}
		return false;
	}

	public void setCellTree(CellTree cellTree) {
		this.tree = cellTree;
	}

}