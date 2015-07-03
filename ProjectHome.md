The **updatable-cell-tree** project aims to showcase an example implementation of GWT's CellTree widget with the ability to update the nodes (add/remove) and edit the text labels using an in-place customized version of EditTextCell for the leaf labels that is activated by a double-click on the label.

As some may already know, the GWT CellTree is a complex component such that it isn't immediately obvious how to accomplish the goal of an updatable tree. Hopefully after examining the source of this project you will be able to adapt your implementation of CellTree to also become updatable.

The example **UpdatableCellTreeTest** utilizes the following classes:

  * CustomEditTextCell - a fork/copy of the EditTextCell that is based on UpdatableTreeNode instead of String with the added feature of being able to set the input text box size.

  * UpdatableTreeModel - the model implementation that will correctly manages add/remove of nodes as well as traversing the tree to ensure parent nodes are closed when last child is removed (but you must set the celltree on the model).

  * UpdatableTreeNode - an interface for the base type used by the CellTree and TreeModel (instead of String) and AbstractUpdatableTreeNode is a base implementation that you can extend and implement !populateTreeNodeList to add nodes to the tree.

See example live demo https://updatable-cell-tree.googlecode.com/git/war/UpdatableCellTreeTest.html