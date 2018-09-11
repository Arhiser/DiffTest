package com.arhiser.difftest.objdiff.ui.ViewHolder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arhiser.difftest.objdiff.difs.DiffService;
import com.arhiser.difftest.objdiff.difs.mapping.BasicDiffMappings;
import com.arhiser.difftest.objdiff.difs.mapping.DiffDispatcher;
import com.arhiser.difftest.objdiff.difs.mapping.DiffMappings;
import com.arhiser.difftest.objdiff.ui.controller.Controller;

import java.util.HashMap;

public class ChangesViewHolder<C extends Controller> extends BaseViewHolder<DiffService.DiffData> {

    DiffDispatcher diffDispatcher;
    C controller;

    public ChangesViewHolder(View root, C controller) {
        super(root);
        diffDispatcher = new DiffDispatcher(getDispatchTable(), getDiffMappings());
        this.controller = controller;
    }

    protected HashMap<String, Object> getDispatchTable() {
        HashMap<String, Object> dispatchTable = new HashMap<>();
        fillDispatchTable(dispatchTable, getView());
        return dispatchTable;
    }

    protected DiffMappings getDiffMappings() {
        return new BasicDiffMappings().addMapping(TextView.class, String.class, (object, value) ->
                object.setText(TextUtils.isEmpty(value) ? "" : value));
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
