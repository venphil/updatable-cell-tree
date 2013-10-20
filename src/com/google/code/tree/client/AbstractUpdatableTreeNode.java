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

import com.google.gwt.view.client.ListDataProvider;

public abstract class AbstractUpdatableTreeNode implements UpdatableTreeNode {

	private ListDataProvider<UpdatableTreeNode> dataProvider;
	private String label;
	private UpdatableTreeNode parent = null;

	public AbstractUpdatableTreeNode(String label) {
		parent = null;
		this.label = label;
		dataProvider = new ListDataProvider<UpdatableTreeNode>();
	}

	public boolean hasChildren() {
		return !(dataProvider.getList().isEmpty());
	}

	public int getChildCount() {
		return dataProvider.getList().size();
	}

	public void addChild(UpdatableTreeNode child) {
		child.setParent(this);
		dataProvider.getList().add(child);
	}

	public void removeChild(UpdatableTreeNode child) {
		for (UpdatableTreeNode sib : child.getDataProvider().getList()) {
			removeChild(sib);
		}
		child.setParent(null);
		dataProvider.getList().remove(child);
	}

	public UpdatableTreeNode getParent() {
		return parent;
	}

	public void setParent(UpdatableTreeNode parent) {
		this.parent = parent;
	}

	public String getLabel() {
		return label;
	}

	public ListDataProvider<UpdatableTreeNode> getDataProvider() {
		return dataProvider;
	}
}