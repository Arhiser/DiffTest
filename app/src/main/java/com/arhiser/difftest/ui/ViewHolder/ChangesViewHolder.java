package com.arhiser.difftest.ui.ViewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arhiser.difftest.difs.Cancellable;
import com.arhiser.difftest.difs.DiffService;
import com.arhiser.difftest.difs.mapping.BasicDiffMappings;
import com.arhiser.difftest.difs.mapping.DiffDispatcher;
import com.arhiser.difftest.difs.mapping.DiffMappings;
import com.arhiser.difftest.model.ChangeDispatcher;

import java.util.HashMap;

import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;

public class ChangesViewHolder extends BaseViewHolder<DiffService.DiffData> {

    DiffDispatcher diffDispatcher;

    public ChangesViewHolder(View root) {
        super(root);
        diffDispatcher = new DiffDispatcher(getDispatchTable(), getDiffMappings());
    }

    protected HashMap<String, Object> getDispatchTable() {
        HashMap<String, Object> dispatchTable = new HashMap<>();
        fillDispatchTable(dispatchTable, getView());
        return dispatchTable;
    }

    protected DiffMappings getDiffMappings() {
        return new BasicDiffMappings().addMapping(TextView.class, String.class, TextView::setText);
    }

    @Override
    public void bind(DiffService.DiffData data) {
        diffDispatcher.dispatch(data);
    }

    private void fillDispatchTable(HashMap<String, Object> dispatchTable, View root) {
        if (root.getTag() != null && root.getTag() instanceof String) {
            dispatchTable.put((String) root.getTag(), root);
        }
        if (root instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                fillDispatchTable(dispatchTable, viewGroup.getChildAt(i));
            }
        }
    }
}
