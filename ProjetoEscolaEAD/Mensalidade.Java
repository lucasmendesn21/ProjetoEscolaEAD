public class Mensalidade
{
    private double valor;
    private boolean pago;

    public Mensalidade(double valor, boolean pago)
    {
        this.valor = valor;
        this.pago = pago;
    }

    public double getValor()
    {
        return valor;
    }

    public boolean isPago()
    {
        return pago;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public void setPago(boolean pago)
    {
        this.pago = pago;
    }

    public void darBaixa()
    {
        pago = true;
    }
}