public class Aluno
{
    private int codigo;
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;

    private Curso cursoMatriculado;

    // Parte 04
    private double[] notas = new double[3];
    private boolean[] lancada = new boolean[3];

    // Parte 05
    private Mensalidade[] mensalidades;
    private int numParcelas;

    public Aluno(int codigo, String nome, String dataNascimento,
                 String email, String senha)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;

        cursoMatriculado = null;

        for (int i = 0; i < 3; i++)
        {
            notas[i] = 0.0;
            lancada[i] = false;
        }

        mensalidades = null;
        numParcelas = 0;
    }

    // Getters

    public int getCodigo()
    {
        return codigo;
    }

    public String getNome()
    {
        return nome;
    }

    public String getDataNascimento()
    {
        return dataNascimento;
    }

    public String getEmail()
    {
        return email;
    }

    public String getSenha()
    {
        return senha;
    }

    public Curso getCursoMatriculado()
    {
        return cursoMatriculado;
    }

    // Setters

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public void setDataNascimento(String dataNascimento)
    {
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

    public void setCursoMatriculado(Curso cursoMatriculado)
    {
        this.cursoMatriculado = cursoMatriculado;
    }

    // Parte 01

    public void exibeDados()
    {
        System.out.println("Código: " + codigo);
        System.out.println("Nome: " + nome);
        System.out.println("Data de nascimento: " + dataNascimento);
        System.out.println("E-mail: " + email);

        if (cursoMatriculado != null)
        {
            System.out.println("Curso: " + cursoMatriculado.getNome());
        }
        else
        {
            System.out.println("Curso: Nenhum curso matriculado");
        }
    }

    // Parte 04

    public void lancarNotas(double nota1, double nota2, double nota3)
    {
        if (nota1 >= 0 && nota1 <= 10)
        {
            notas[0] = nota1;
            lancada[0] = true;
        }

        if (nota2 >= 0 && nota2 <= 10)
        {
            notas[1] = nota2;
            lancada[1] = true;
        }

        if (nota3 >= 0 && nota3 <= 10)
        {
            notas[2] = nota3;
            lancada[2] = true;
        }
    }

    public double calcularMedia()
    {
        double soma = 0;

        for (int i = 0; i < 3; i++)
        {
            soma += notas[i];
        }

        return soma / 3;
    }

    public boolean possuiTodasAsNotas()
    {
        for (int i = 0; i < 3; i++)
        {
            if (!lancada[i])
            {
                return false;
            }
        }

        return true;
    }

    public void exibirNotas()
    {
        System.out.println("Notas do aluno: " + nome);

        for (int i = 0; i < 3; i++)
        {
            if (lancada[i])
            {
                System.out.println("Nota " + (i + 1) + ": " + notas[i]);
            }
            else
            {
                System.out.println("Nota " + (i + 1) + ": Não lançada");
            }
        }

        if (possuiTodasAsNotas())
        {
            System.out.println("Média: " + calcularMedia());
        }
        else
        {
            System.out.println("Média: ainda não disponível.");
        }
    }

    // Parte 05

    public void adicionarMensalidades(double[] valores)
    {
        if (valores == null || valores.length == 0)
        {
            mensalidades = null;
            numParcelas = 0;
            return;
        }

        numParcelas = valores.length;
        mensalidades = new Mensalidade[numParcelas];

        for (int i = 0; i < numParcelas; i++)
        {
            mensalidades[i] = new Mensalidade(valores[i], false);
        }
    }

    public void exibirMensalidades()
    {
        if (mensalidades == null || numParcelas == 0)
        {
            System.out.println("Nenhuma mensalidade cadastrada.");
            return;
        }

        System.out.println("Financeiro do aluno: " + nome);

        for (int i = 0; i < numParcelas; i++)
        {
            System.out.print("Parcela " + (i + 1));
            System.out.print(" - R$ " + mensalidades[i].getValor());

            if (mensalidades[i].isPago())
            {
                System.out.println(" - PAGA");
            }
            else
            {
                System.out.println(" - PENDENTE");
            }
        }
    }

    public void pagarMensalidade(int indice)
    {
        if (mensalidades == null)
        {
            System.out.println("Nenhuma mensalidade cadastrada.");
            return;
        }

        if (indice < 0 || indice >= numParcelas)
        {
            System.out.println("Número de parcela inválido.");
            return;
        }

        if (mensalidades[indice].isPago())
        {
            System.out.println("Essa parcela já está paga.");
            return;
        }

        mensalidades[indice].darBaixa();

        System.out.println("Pagamento realizado com sucesso.");
    }

    public boolean possuiMensalidades()
    {
        return mensalidades != null && numParcelas > 0;
    }
}