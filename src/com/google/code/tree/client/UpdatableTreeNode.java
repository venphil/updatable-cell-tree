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

public interface UpdatableTreeNode {

	public boolean hasChildren();
	
	public int getChildCount();
	
	public void addChild(UpdatableTreeNode child);
	
	public void removeChild(UpdatableTreeNode child);

	public UpdatableTreeNode getParent();

	public void setParent(UpdatableTreeNode parent);

	public String getLabel();

	public ListDataProvider<UpdatableTreeNode> getDataProvider();
}