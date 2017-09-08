package entidades.blocos;

import entidades.TipoBloco;
import factories.BlocoId;
import factories.ContainerId;
import interfaces.IBinary;
import utils.ByteArrayConcater;
import utils.ByteArrayUtils;
import utils.GlobalVariables;

public class BlocoDadoHeader implements IBinary {

    private ContainerId containerId;
    private BlocoId blocoId;
    private TipoBloco tipoBloco;
    private int tamanhoUsado;

    public BlocoDadoHeader() {
        this.containerId = ContainerId.create();
        this.blocoId = BlocoId.create();
        this.tipoBloco = TipoBloco.DADOS;
        this.tamanhoUsado = 0;
    }

    public BlocoDadoHeader(int containerId, int blocoId) {
        this.containerId = ContainerId.create(containerId);
        this.blocoId = BlocoId.create(blocoId);
        this.tipoBloco = TipoBloco.DADOS;
        this.tamanhoUsado = 0;
    }

    public int getTamanhoUsado() {
        return this.tamanhoUsado;
    }

    public int getContainerId() {
        return containerId.getValue();
    }

    public int getBlocoId() {
        return blocoId.getValue();
    }

    @Override
    public byte[] toByteArray() {

        byte[] containerIdBytes = this.containerId.toByteArray();
        byte[] blocoIdBytes = this.blocoId.toByteArray();

        ByteArrayConcater byteConcater = new ByteArrayConcater(8);
        byteConcater
                .concat(containerIdBytes)
                .concat(blocoIdBytes);

        return byteConcater.getFinalByteArray();
    }

    @Override
    public BlocoDadoHeader fromByteArray(byte[] byteArray) {

        byte[] containerIdBytes = ByteArrayUtils.subArray(byteArray, 0, 1);  // 0   ContainerId
        byte[] blocoIdBytes = ByteArrayUtils.subArray(byteArray, 1, 3);      // 1-3 BlocoId
        byte[] tipoBlocoBytes = ByteArrayUtils.subArray(byteArray, 4, 1);    // 4   TipoBloco
        byte[] tamanhoUsadoBytes = ByteArrayUtils.subArray(byteArray, 5, 3); // 5-7 Tamanho Utilizado

        this.containerId = this.containerId.fromByteArray(containerIdBytes);
        this.blocoId =  this.blocoId.fromByteArray(blocoIdBytes);
        this.tipoBloco = ByteArrayUtils.byteArrayToEnum(tipoBlocoBytes, TipoBloco.values());
        this.tamanhoUsado = ByteArrayUtils.byteArrayToInt(tamanhoUsadoBytes);

        return this;
    }

    public void incrementarTamanhoUsado(int tamanho) {
        this.tamanhoUsado += tamanho;
    }
}
