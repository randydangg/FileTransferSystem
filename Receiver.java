import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.util.Date.*;
import java.lang.*;

public class Receiver extends javax.swing.JFrame{
	
	private int sender_port, receiver_port, ackCount = -1, connection_type = 1;	//default is reliable connection
	private InetAddress senderIP;
	private String save_file;
	private DatagramSocket datagramSocket;
	private long start_time, finish_time;
	private int BUFFER_SIZE = 128;	//max size of each packet, 128 will be the default size, in order to successfully receive first packet
	
	//all fields must be filled before attempting connection
	public Receiver() {
		//initialize GUI
		initComponents();
	}
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reliableButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        ipLabel = new javax.swing.JLabel();
        destPortLabel = new javax.swing.JLabel();
        srcPortLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();
        restrictLabel = new javax.swing.JLabel();
        ipInput = new javax.swing.JTextField();
        destPortInput = new javax.swing.JTextField();
        srcPortInput = new javax.swing.JTextField();
        fileInput = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        numPktsText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Receiver"));

        ipLabel.setText("IP Address Sender:");

        destPortLabel.setText("Destination Port:");

        srcPortLabel.setText("Source Port:");

        fileLabel.setText("Save File:");

        restrictLabel.setText("<html> Please be sure that sender <br/> "
        		+ "has sent something before <br/>"
        		+ "hitting reliabiltiy options. </html>");
        
        fileInput.getDocument().addDocumentListener(new DocumentListener() {
        	public void changedUpdate(DocumentEvent e) {
        		changed();
        	}
        	public void removeUpdate(DocumentEvent e) {
        		changed();
        	}
        	public void insertUpdate(DocumentEvent e) {
        		changed();
        	}
        });
        
        srcPortInput.getDocument().addDocumentListener(new DocumentListener() {
        	public void changedUpdate(DocumentEvent e) {
        		changed();
        	}
        	public void removeUpdate(DocumentEvent e) {
        		changed();
        	}
        	public void insertUpdate(DocumentEvent e) {
        		changed();
        	}
        });
        
        destPortInput.getDocument().addDocumentListener(new DocumentListener() {
        	public void changedUpdate(DocumentEvent e) {
        		changed();
        	}
        	public void removeUpdate(DocumentEvent e) {
        		changed();
        	}
        	public void insertUpdate(DocumentEvent e) {
        		changed();
        	}
        });
        
        ipInput.getDocument().addDocumentListener(new DocumentListener() {
        	public void changedUpdate(DocumentEvent e) {
        		changed();
        	}
        	public void removeUpdate(DocumentEvent e) {
        		changed();
        	}
        	public void insertUpdate(DocumentEvent e) {
        		changed();
        	}
        });

        reliableButtonGroup.add(jRadioButton1);
        jRadioButton1.setText("Reliable");
        jRadioButton1.setEnabled(false);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        reliableButtonGroup.add(jRadioButton2);
        jRadioButton2.setText("Unreliable");
        jRadioButton2.setEnabled(false);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        numPktsText.setEditable(false);
        numPktsText.setColumns(20);
        numPktsText.setRows(2);
        numPktsText.setText("Number of in-order packets received: 0");
        jScrollPane1.setViewportView(numPktsText);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(destPortLabel)
                            .addGap(48, 48, 48)
                            .addComponent(destPortInput))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(ipLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                            .addComponent(ipInput, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(srcPortLabel)
                                .addComponent(fileLabel)
//                                .addComponent(mdsLabel)
                                )
                            .addGap(26, 26, 26)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                .addComponent(mdsInput)
                                .addComponent(fileInput)
                                .addComponent(srcPortInput)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(restrictLabel)))
                .addContainerGap(10, Short.MAX_VALUE))	//old value = 30
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipLabel)
                    .addComponent(ipInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(destPortLabel)
                    .addComponent(destPortInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(srcPortLabel)
                    .addComponent(srcPortInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLabel)
                    .addComponent(fileInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(mdsLabel)
//                    .addComponent(mdsInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(restrictLabel))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
	
	public void changed() {
		if (fileInput.getText().equals("") || srcPortInput.getText().equals("") || destPortInput.getText().equals("") || ipInput.getText().equals("")){
			jRadioButton1.setEnabled(false);
			jRadioButton2.setEnabled(false);
		}
		else {
			jRadioButton1.setEnabled(true);
			jRadioButton2.setEnabled(true);
		}
	}
	
    private void ipInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipInputActionPerformed
    
    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    	System.out.println("reliable button pressed");
    	connection_type = 1;
    	connect();
    }//GEN-LAST:event_jRadioButton2ActionPerform)
    
    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    	System.out.println("unreliable button pressed");
    	connection_type = 0;
    	connect();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
	private void connect() {
		try {
			senderIP = InetAddress.getByName(ipInput.getText());
			sender_port = Integer.parseInt(srcPortInput.getText());
			receiver_port = Integer.parseInt(destPortInput.getText());
			save_file = fileInput.getText();
				
			datagramSocket = new DatagramSocket(receiver_port);	//create datagram socket
			if (connection_type == 0){	//unreliable transfer
				transferUnreliable();
			}
			else if (connection_type == 1) {	//reliable transfer
				transferReliable();
			}
		}
		catch (SocketException e) {
			System.out.println("Error connecting Socket");
		} 
		catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	private void transferReliable() {
		//reliable transfer
		byte[] data_receive = new byte[BUFFER_SIZE];	//size of these buffers might have to be set by the sender I believe
		//default byte arrays of size 128
		byte[] acknowledgement = new byte[4];	//first 4 bytes will hold acknowledgement
		byte[] data_save = new byte[BUFFER_SIZE - 4];	//every byte afterwards will hold the file data
		byte[] EOTFile = new byte[BUFFER_SIZE - 4];
		byte ack = 0;
		boolean continue_sending = true;
		DatagramPacket send_packet;
		DatagramPacket receive_packet;
		
		System.out.print("Beginning to provide handshaking");
		//receive initial handshake from sender
		DatagramPacket receive_init = new DatagramPacket(data_receive, data_receive.length);
		try {
			datagramSocket.receive(receive_init);
			data_receive = receive_init.getData();
			BUFFER_SIZE = data_receive[3];	//set new buffer size
			//and then initialize byte arrays accordingly 
			data_receive = new byte[BUFFER_SIZE];
			data_save = new byte[BUFFER_SIZE - 4];
			EOTFile = new byte[BUFFER_SIZE - 4];
		} 
		catch (IOException e) {
			System.out.println("Error receiving packet");
		}
		
		//send acknowledgement for initial handshake
		//consider sending another ACK num for handshake instead of 0?
		System.out.println("Preparing to send acknowledgement of handshake");
		acknowledgement[0] = ack;
		DatagramPacket send_init = new DatagramPacket(acknowledgement, acknowledgement.length, senderIP, sender_port);
		try {
			datagramSocket.send(send_init);
		}
		catch (IOException e) {
			System.out.println("Error sending packet");
		}
		ack = ackSwitch(ack);
		
		while (continue_sending) {
			receive_packet = new DatagramPacket(data_receive, data_receive.length);
			try {
				datagramSocket.receive(receive_packet);
				data_receive = new byte[BUFFER_SIZE];
				data_receive = receive_packet.getData();	//get data 
				System.out.println("received packet seq# " + data_receive[0]);
				
				if (data_receive[0] == ack) {	//make sure that the ack number and seq num match
					for (int i = 4; i < data_receive.length - 4; i++) {	//If receiver had to break down data into multiple packets
						//store data into save file
						data_save[i-4] = data_receive[i];
					}
					writeToFile(data_save);	//write temporary save file to the output file
					acknowledgement[0] = ack;	//create ACK to send to sender
					send_packet = new DatagramPacket(acknowledgement, acknowledgement.length, senderIP, sender_port);
					try {
						datagramSocket.send(send_packet);
						System.out.println("Sent ACK# " + acknowledgement);
					}
					catch (IOException e) {
						System.out.println("Error sending packet");
					}
					ack = ackSwitch(ack);	//switch ack from 0 to 1 to start acknowledgement
				}
				else if (data_receive[0] == 2) {	//EOT bit to end transmission
					continue_sending = false;
					System.out.println("Data Received: " + data_receive[1]);
					for (int i = 4; i < data_receive.length - 4; i++) {	//If receiver had to break down data into multiple packets
						//store data into bytefile
						EOTFile[i-4] = data_receive[i];
					}
					
					try (FileOutputStream writer = new FileOutputStream(save_file, true)){
						for (int j = 0; j < (int)data_receive[1]; j++) {
							writer.write(EOTFile[j]);
						}
					}
					
					acknowledgement[0] = 2;		//set ACK num to EOT bit and send to sender, notifying them that transmission has ended
					send_packet = new DatagramPacket(acknowledgement, acknowledgement.length, senderIP, sender_port);
					try {
						datagramSocket.send(send_packet);
						System.out.println("Sent ACK# " + acknowledgement);
					}
					catch (IOException e) {
						System.out.println("Error sending packet");
					}
					System.out.println("End of Transmission");
					numPktsText.setText("Number of in-order packets received: " + ackCount);
					datagramSocket.close();
				}
			}
			catch (IOException e) {
				System.out.println("Error receiving datagram");
			}
		}
	}
	
	private void transferUnreliable() {
		//unreliable transfer
		byte[] data_receive = new byte[BUFFER_SIZE];	//initialize 
		//default byte arrays of size 128
		byte[] acknowledgement = new byte[4];	//first 4 bytes will hold acknowledgement
		byte[] data_save = new byte[BUFFER_SIZE - 4];	//every byte afterwards will hold the file data
		byte[] EOTFile = new byte[BUFFER_SIZE - 4];
		byte ack = 0;
		int packet_count = 0;
		boolean continue_sending = true;
		DatagramPacket send_packet;
		DatagramPacket receive_packet;
		
		//receive initial handshake from user
		System.out.print("Beginning to provide handshaking");
		//receive initial handshake from sender
		DatagramPacket receive_init = new DatagramPacket(data_receive, data_receive.length);
		try {
			datagramSocket.receive(receive_init);
			data_receive = receive_init.getData();
			BUFFER_SIZE = data_receive[3];	//set new buffer size
			//and then initialize byte arrays accordingly 
			data_receive = new byte[BUFFER_SIZE];
			data_save = new byte[BUFFER_SIZE - 4];
			EOTFile = new byte[BUFFER_SIZE - 4];
		} 
		catch (IOException e) {
			System.out.println("Error receiving packet");
		}
		
		//send acknowledgement for initial handshake
		//consider sending another ACK num for handshake instead of 0?
		System.out.println("Preparing to send acknowledgement of handshake");
		acknowledgement[0] = ack;
		DatagramPacket send_init = new DatagramPacket(acknowledgement, acknowledgement.length, senderIP, sender_port);
		try {
			datagramSocket.send(send_init);
		}
		catch (IOException e) {
			System.out.println("Error sending packet");
		}
		ack = ackSwitch(ack);
		
		while (continue_sending) {
			receive_packet = new DatagramPacket(data_receive, data_receive.length);
			try {
				datagramSocket.receive(receive_packet);
				data_receive = new byte[BUFFER_SIZE];
				data_receive = receive_packet.getData();	//get data 
				System.out.println("received packet seq# " + data_receive[0]);
				
				if (data_receive[0] == ack) {	//make sure that the ack number and seq num match
					if (packet_count != 10) {
						for (int i = 4; i < data_receive.length - 4; i++) {	//If receiver had to break down data into multiple packets
							//store data into save file
							data_save[i-4] = data_receive[i];
						}
						writeToFile(data_save);	//write temporary save file to the output file
						acknowledgement[0] = ack;	//create ACK to send to sender
						send_packet = new DatagramPacket(acknowledgement, acknowledgement.length, senderIP, sender_port);
						try {
							datagramSocket.send(send_packet);
							System.out.println("Sent ACK# " + acknowledgement);
						}
						catch (IOException e) {
							System.out.println("Error sending packet");
						}
						packet_count += 1;
						ack = ackSwitch(ack);	//switch ack from 0 to 1 to start acknowledgement
					}
					else {
						packet_count = 0;
						System.out.println("Oops, packet was dropped, Receiver is now waiting for retransmission");					}
				}
				else if (data_receive[0] == 2) {	//EOT bit to end transmission
					continue_sending = false;
					System.out.println("Data Received: " + data_receive[1]);
					for (int i = 4; i < data_receive.length - 4; i++) {	//If receiver had to break down data into multiple packets
						//store data into bytefile
						EOTFile[i-4] = data_receive[i];
					}
					//write data to save file without padding the rest of the file
					try (FileOutputStream writer = new FileOutputStream(save_file, true)){
						for (int j = 0; j < (int)data_receive[1]; j++) {
							writer.write(EOTFile[j]);
						}
					}
					acknowledgement[0] = 2;		//set ACK num to EOT bit and send to sender
					send_packet = new DatagramPacket(acknowledgement, acknowledgement.length, senderIP, sender_port);
					try {
						datagramSocket.send(send_packet);
						System.out.println("Sent ACK# " + acknowledgement);
					}
					catch (IOException e) {
						System.out.println("Error sending packet");
					}
					System.out.println("End of Transmission");
					numPktsText.setText("Number of in-order packets received: " + ackCount);
					datagramSocket.close();
				}
				
			}
			catch (IOException e) {
				System.out.println("Error receiving datagram");
			}
		}
	}
	
	private byte ackSwitch(byte ack) {	//switch b/w ACK 0 and 1
		if (ack == 0) {
			ack = 1;
		}
		else {
			ack = 0;
		}
		ackCount += 1;
		return ack;
	}
	
	private void writeToFile(byte[] data) {
		FileOutputStream writer;
		try {
			writer = new FileOutputStream(save_file, true);
		}
		catch (FileNotFoundException e) {
			System.out.println("Error writing to save file");
		}
	}
	
	public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReceiverUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReceiverUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReceiverUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReceiverUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Receiver().setVisible(true);
            }
        });
    }
	
	 private javax.swing.JTextField destPortInput;
	 private javax.swing.JLabel destPortLabel;
	 private javax.swing.JTextField fileInput;
	 private javax.swing.JLabel fileLabel;
	 private javax.swing.JTextField ipInput;
	 private javax.swing.JLabel ipLabel;
	 private javax.swing.JPanel jPanel1;
	 private javax.swing.JRadioButton jRadioButton1;
	 private javax.swing.JRadioButton jRadioButton2;
	 private javax.swing.JScrollPane jScrollPane1;
//	 private javax.swing.JTextField mdsInput;
	 private javax.swing.JLabel restrictLabel;
	 private javax.swing.JTextArea numPktsText;
	 private javax.swing.ButtonGroup reliableButtonGroup;
	 private javax.swing.JTextField srcPortInput;
	 private javax.swing.JLabel srcPortLabel;
}
