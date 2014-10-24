package keywords;

import java.awt.Color;

public class list extends javax.swing.JFrame {
    
    public list() {
        initComponents();
        setSize(1366, 768);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Serif", 3, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ARM  APPLICATION  ON  RDF  DATASETS");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(220, 30, 1070, 60);

        jLabel3.setFont(new java.awt.Font("Tekton Pro Cond", 1, 36)); // NOI18N
        jLabel3.setText("Start  Application");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
        });
        jPanel1.add(jLabel3);
        jLabel3.setBounds(80, 360, 220, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/keywords/nexus-dna-force-directedlayout-2011-05-15.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(-6, 0, 1370, 760);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1370, 760);

        pack();
    }// </editor-fold>

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {
        
        this.setVisible(false);
    }

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {
        jLabel3.setForeground(Color.yellow);
    }

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {
       
        jLabel3.setForeground(Color.black);
    }
    
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(list.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(list.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(list.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(list.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new list().setVisible(true);
            }
        });
    }
    
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
}
