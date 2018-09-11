package com.arhiser.difftest.objdiff.difs.mapping;

import com.arhiser.difftest.objdiff.difs.DiffService;

import java.util.Map;

import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;

public class DiffDispatcher implements DiffNode.Visitor {

    Map<String, Object> dispatchTable;
    DiffMappings mappings;
    DiffService.DiffData diffData;

    public DiffDispatcher(Map<String, Object> dispatchTable, DiffMappings mappings) {
        this.dispatchTable = dispatchTable;
        this.mappings = mappings;
    }

    public void dispatch(DiffService.DiffData diffData) {
        this.diffData = diffData;
        node(diffData.getDifs(), null);
    }

    @Override
    public void node(DiffNode node, Visit visit) {
        Object affected = dispatchTable.get(node.getPropertyName());
        if (affected != null) {
            mappings.map(affected, node.get(diffData.getObjectNew()));
        }
        node.visitChildren(this);
    }
}
