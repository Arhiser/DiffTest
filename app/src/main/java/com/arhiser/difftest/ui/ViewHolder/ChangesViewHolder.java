package com.arhiser.difftest.ui.ViewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arhiser.difftest.difs.Cancellable;
import com.arhiser.difftest.difs.DiffService;
import com.arhiser.difftest.model.ChangeDispatcher;

import java.util.HashMap;

import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;

public class ChangesViewHolder extends BaseViewHolder<DiffService.DiffData> implements DiffNode.Visitor {

    HashMap<String, View> dispatchTable = new HashMap<>();
    DiffService.DiffData diffData;

    public ChangesViewHolder(View root) {
        super(root);
        fillDispatchTable(root);
    }

    @Override
    public void bind(DiffService.DiffData data) {
        this.diffData = data;
        dispatch();
    }

    private void dispatch() {
        node(diffData.getDifs(), null);
    }

    private void fillDispatchTable(View root) {
        if (root.getTag() != null && root.getTag() instanceof String) {
            dispatchTable.put((String) root.getTag(), root);
        }
        if (root instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                fillDispatchTable(viewGroup.getChildAt(i));
            }
        }
    }

    @Override
    public void node(DiffNode node, Visit visit) {
        View affectedView = dispatchTable.get(node.getPropertyName());
        if (affectedView != null) {
            if (affectedView instanceof TextView) {
                ((TextView)affectedView).setText(node.get(diffData.getObjectNew()).toString());
            }
        }
        node.visitChildren(this);
    }
}
