/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazzify;

import java.io.IOException;
import org.netbeans.spi.editor.document.OnSaveTask;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.execution.NbProcessDescriptor;
import org.openide.util.Exceptions;

/**
 *
 * @author Azzuwan
 */
public class DocSaveTask implements OnSaveTask {
    
    private final Context context;

    public DocSaveTask(Context cntxt) {
        this.context = cntxt;
    }

    @Override
    public void performTask() {
        NotifyDescriptor d = new NotifyDescriptor.Message("Hi This is invoked after you save a doc");
        DialogDisplayer.getDefault().notify(d);
        NbProcessDescriptor process = new NbProcessDescriptor("vertx", "", "Vertx command");
        try {
            process.exec();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void runLocked(Runnable r) {
        r.run();
    }

    @Override
    public boolean cancel() {
        return true;
    }
    
}
