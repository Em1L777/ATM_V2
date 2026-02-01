package com.atm.v2.api;

import com.atm.v1.api.ITechnicianActions;

public interface ITechActionsV2 extends ITechnicianActions {
    // Inherits viewATMStatus() from V1
    void replenish(double cash, int ink, int paper);
    void runDiagnostics();
    void performUpgrade();
}