/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazzify;

import java.awt.Color;
import java.io.IOException;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;

import org.netbeans.spi.editor.document.OnSaveTask;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;
import org.openide.windows.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Azzuwan
 */
public class DocSaveTask implements OnSaveTask {

    private static final RequestProcessor rp = new RequestProcessor();
    private Context context;
    private InputOutput io = IOProvider.getDefault().getIO("Vertx", false);
    private RequestProcessor.Task task;
    private BufferedWriter out = null;
    private Socket sock;
    private BufferedReader in;
    private StringBuilder sb;

    public String sendMessage() {
        String res = "";
        try {

            out.write("TEST\r\n");
            out.flush();
            res = in.readLine();
            System.out.println("Response: " + res);
            io.getOut().println(res);

        } catch (IOException ex) {
            Logger.getLogger(DocSaveTask.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(DocSaveTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return res;
    }
    
    public String restartVertx() {
        String res = "";
        try {

            out.write("RESTART\r\n");
            out.flush();
//            while((res = in.readLine()) != null)
//            {
//                System.out.println("Response: " + res);
//                io.getOut().println(res);
//            }
        res = in.readLine();
        io.getOut().println(res);

        } catch (IOException ex) {
            Logger.getLogger(DocSaveTask.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(DocSaveTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return res;
    }

    public DocSaveTask() {

    }

    public DocSaveTask(Context cntxt) {
        this.context = cntxt;
        task = rp.create(new Runnable() {
            public void run() {
                try {
                    sock = new Socket("localhost", 2012);
                    out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                    in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    sb = new StringBuilder();
                    io.select();
                    io.getOut().println("SOCKET CLIENT STARTED");
                    io.getOut().println(in.readLine());
                    restartVertx();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }
        });
    }

    @Override
    public void performTask() {
        Project p = TopComponent.getRegistry().getActivated().getLookup().lookup(Project.class);
        if (p == null) {
            DataObject dob = TopComponent.getRegistry().getActivated().getLookup().lookup(DataObject.class);
            if (dob != null) {
                FileObject fo = dob.getPrimaryFile();
                p = FileOwnerQuery.getOwner(fo); 
                task.run();
            }
        }

    }

    @Override
    public void runLocked(Runnable r
    ) {
        r.run();
    }

    @Override
    public boolean cancel() {
        return true;
    }

}
