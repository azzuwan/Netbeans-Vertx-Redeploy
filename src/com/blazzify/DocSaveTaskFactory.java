/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazzify;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.editor.mimelookup.MimeRegistrations;
import org.netbeans.spi.editor.document.OnSaveTask;

/**
 *
 * @author Azzuwan
 */
 @MimeRegistrations({
        @MimeRegistration(mimeType = "text/html", service = OnSaveTask.Factory.class),
        @MimeRegistration(mimeType = "text/css", service = OnSaveTask.Factory.class),
        @MimeRegistration(mimeType = "text/java", service = OnSaveTask.Factory.class),
        @MimeRegistration(mimeType = "text/javascript", service = OnSaveTask.Factory.class),
        @MimeRegistration(mimeType = "text/x-java", service = OnSaveTask.Factory.class),
        @MimeRegistration(mimeType = "text/x-javascript", service = OnSaveTask.Factory.class),
    })
    

public class DocSaveTaskFactory implements OnSaveTask.Factory {
   
    @Override
    public OnSaveTask createTask(OnSaveTask.Context cntxt) {
        return new DocSaveTask(cntxt);
    }
    
   
    
}
