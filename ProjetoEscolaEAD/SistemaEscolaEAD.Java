import java.util.Scanner;

public class SistemaEscolaEAD
{
    private Curso[] cursos;
    private int totalCursos;

    // Array bidimensional obrigatório
    private Curso[][] matrizCursos;

    private ListaDeAlunos listaAlunos;

    private Scanner entrada;

    public SistemaEscolaEAD(int capacidadeCursos,
                            int capacidadeAlunos,
                            int linhasMatriz,
                            int colunasMatriz)
    {
        cursos = new Curso[capacidadeCursos];
        totalCursos = 0;

        matrizCursos = new Curso[linhasMatriz][colunasMatriz];

        listaAlunos = new ListaDeAlunos(capacidadeAlunos);

        entrada = new Scanner(System.in);
    }

    // ==========================================
    // PARTE 02 - CADASTRO DE CURSOS
    // ==========================================

    public void cadastrarCursos()
    {
        if (cursos.length == 0)
        {
            System.out.println("Não há espaço para cadastrar cursos.");
            return;
        }

        for (int i = 0; i < cursos.length; i++)
        {
            System.out.println();
            System.out.println("=== Cadastro do curso " + (i + 1) + " ===");

            System.out.print("Digite o código do curso: ");
            int codigo = entrada.nextInt();
            entrada.nextLine();

            // Verifica código duplicado
            if (buscarCurso(codigo) != null)
            {
                System.out.println("Código já utilizado.");
                i--;
                continue;
            }

            System.out.print("Digite o nome do curso: ");
            String nome = entrada.nextLine();

            System.out.print("Digite a duração do curso em horas: ");
            int duracao = entrada.nextInt();
            entrada.nextLine();

            cursos[totalCursos] = new Curso(codigo, nome, duracao);

            // Coloca o curso também na matriz bidimensional
            cadastrarCursoNaMatriz(
                cursos[totalCursos],
                totalCursos / matrizCursos[0].length,
                totalCursos % matrizCursos[0].length
            );

            totalCursos++;
        }

        System.out.println();
        System.out.println("Cursos cadastrados com sucesso.");

        System.out.println();
        System.out.println("=== MATRIZ DE CURSOS ===");
        exibirMatrizCursos();
    }

    public void exibirCursos()
    {
        if (totalCursos == 0)
        {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }

        for (int i = 0; i < totalCursos; i++)
        {
            System.out.println("-----------------------------");

            cursos[i].exibeDados();

            System.out.println("Alunos matriculados:");

            boolean encontrou = false;

            for (int j = 0; j < listaAlunos.getTotalAlunos(); j++)
            {
                Aluno aluno = listaAlunos.buscarAlunoPorIndice(j);

                if (aluno.getCursoMatriculado() != null &&
                    aluno.getCursoMatriculado().getCodigo()
                    == cursos[i].getCodigo())
                {
                    System.out.println(
                        "- " + aluno.getNome() +
                        " (código " + aluno.getCodigo() + ")"
                    );

                    encontrou = true;
                }
            }

            if (!encontrou)
            {
                System.out.println("- Nenhum aluno matriculado.");
            }
        }

        System.out.println("-----------------------------");
    }

    private Curso buscarCurso(int codigo)
    {
        for (int i = 0; i < totalCursos; i++)
        {
            if (cursos[i].getCodigo() == codigo)
            {
                return cursos[i];
            }
        }

        return null;
    }

    // ==========================================
    // MATRIZ BIDIMENSIONAL
    // ==========================================

    public void cadastrarCursoNaMatriz(Curso curso,
                                       int linha,
                                       int coluna)
    {
        if (curso == null)
        {
            return;
        }

        if (matrizCursos.length == 0)
        {
            return;
        }

        if (matrizCursos[0].length == 0)
        {
            return;
        }

        if (linha < 0 ||
            linha >= matrizCursos.length ||
            coluna < 0 ||
            coluna >= matrizCursos[0].length)
        {
            return;
        }

        matrizCursos[linha][coluna] = curso;
    }

    public void exibirMatrizCursos()
    {
        if (matrizCursos.length == 0 ||
            matrizCursos[0].length == 0)
        {
            System.out.println("Matriz sem posições.");
            return;
        }

        for (int i = 0; i < matrizCursos.length; i++)
        {
            for (int j = 0; j < matrizCursos[i].length; j++)
            {
                if (matrizCursos[i][j] != null)
                {
                    System.out.print(
                        "[" + matrizCursos[i][j].getNome() + "] "
                    );
                }
                else
                {
                    System.out.print("[Vazio] ");
                }
            }

            System.out.println();
        }
    }

    // ==========================================
    // MENU PRINCIPAL
    // ==========================================

    public void executarMenu()
    {
        int opcao;

        do
        {
            System.out.println();
            System.out.println("==============================");
            System.out.println("       ESCOLA EAD");
            System.out.println("==============================");
            System.out.println("1 - Visualizar Lista de Alunos");
            System.out.println("2 - Adicionar Aluno");
            System.out.println("3 - Sair");
            System.out.println("4 - Verificar Notas do Aluno");
            System.out.println("5 - Verificar Financeiro do Aluno");
            System.out.println("==============================");

            System.out.print("Digite uma opção: ");
            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao)
            {
                case 1:
                    listaAlunos.exibirLista();
                    break;

                case 2:
                    adicionarAluno();
                    break;

                case 3:
                    System.out.println("Sistema encerrado.");
                    break;

                case 4:
                    verificarNotas();
                    break;

                case 5:
                    verificarFinanceiro();
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 3);
    }

    // ==========================================
    // CADASTRO DE ALUNO
    // ==========================================

    private void adicionarAluno()
    {
        System.out.println();
        System.out.println("=== CADASTRO DE ALUNO ===");

        System.out.print("Código: ");
        int codigo = entrada.nextInt();
        entrada.nextLine();

        // Evita código duplicado antes mesmo de criar o objeto
        if (listaAlunos.buscarAluno(codigo) != null)
        {
            System.out.println("Já existe um aluno com esse código.");
            return;
        }

        System.out.print("Nome: ");
        String nome = entrada.nextLine();

        System.out.print("Data de nascimento: ");
        String dataNascimento = entrada.nextLine();

        System.out.print("E-mail: ");
        String email = entrada.nextLine();

        System.out.print("Senha: ");
        String senha = entrada.nextLine();

        System.out.print("O aluno é bolsista? (S/N): ");
        String respostaBolsista = entrada.nextLine();

        Aluno aluno;

        if (respostaBolsista.equalsIgnoreCase("S"))
        {
            System.out.print("Tipo de bolsa: ");
            String tipoBolsa = entrada.nextLine();

            aluno = new AlunoBolsista(
                codigo,
                nome,
                dataNascimento,
                email,
                senha,
                tipoBolsa
            );
        }
        else
        {
            aluno = new Aluno(
                codigo,
                nome,
                dataNascimento,
                email,
                senha
            );
        }

        // Matrícula em curso
        if (totalCursos > 0)
        {
            System.out.println();
            System.out.println("=== CURSOS DISPONÍVEIS ===");

            for (int i = 0; i < totalCursos; i++)
            {
                System.out.println(
                    cursos[i].getCodigo() +
                    " - " +
                    cursos[i].getNome()
                );
            }

            System.out.print("Digite o código do curso: ");
            int codigoCurso = entrada.nextInt();
            entrada.nextLine();

            Curso curso = buscarCurso(codigoCurso);

            if (curso != null)
            {
                aluno.setCursoMatriculado(curso);
                System.out.println("Aluno matriculado no curso.");
            }
            else
            {
                System.out.println("Curso não encontrado.");
            }
        }

        if (listaAlunos.adicionarAluno(aluno))
        {
            System.out.println();
            System.out.println("Aluno cadastrado com sucesso.");

            if (aluno instanceof AlunoBolsista)
            {
                System.out.println(
                    "Aluno cadastrado como bolsista."
                );
            }
        }
        else
        {
            System.out.println("Não foi possível cadastrar o aluno.");
        }
    }

    // ==========================================
    // PARTE 04 - NOTAS
    // ==========================================

    private void verificarNotas()
    {
        System.out.println();
        System.out.println("=== NOTAS DO ALUNO ===");

        System.out.print("Digite o código do aluno: ");
        int codigo = entrada.nextInt();
        entrada.nextLine();

        Aluno aluno = listaAlunos.buscarAluno(codigo);

        if (aluno == null)
        {
            System.out.println("Aluno não encontrado.");
            return;
        }

        // Se ainda não existem três notas, permite lançá-las
        if (!aluno.possuiTodasAsNotas())
        {
            System.out.println();
            System.out.println("As três notas ainda não foram lançadas.");

            System.out.print("Deseja lançar as notas agora? (S/N): ");
            String resposta = entrada.nextLine();

            if (resposta.equalsIgnoreCase("S"))
            {
                double nota1;
                double nota2;
                double nota3;

                do
                {
                    System.out.print("Digite a nota 1 (0 a 10): ");
                    nota1 = entrada.nextDouble();

                    if (nota1 < 0 || nota1 > 10)
                    {
                        System.out.println("Nota inválida.");
                    }

                } while (nota1 < 0 || nota1 > 10);

                do
                {
                    System.out.print("Digite a nota 2 (0 a 10): ");
                    nota2 = entrada.nextDouble();

                    if (nota2 < 0 || nota2 > 10)
                    {
                        System.out.println("Nota inválida.");
                    }

                } while (nota2 < 0 || nota2 > 10);

                do
                {
                    System.out.print("Digite a nota 3 (0 a 10): ");
                    nota3 = entrada.nextDouble();

                    if (nota3 < 0 || nota3 > 10)
                    {
                        System.out.println("Nota inválida.");
                    }

                } while (nota3 < 0 || nota3 > 10);

                entrada.nextLine();

                aluno.lancarNotas(nota1, nota2, nota3);

                System.out.println("Notas lançadas com sucesso.");
            }
        }

        System.out.println();
        aluno.exibirNotas();
    }

    // ==========================================
    // PARTE 05 - FINANCEIRO
    // ==========================================

    private void verificarFinanceiro()
    {
        System.out.println();
        System.out.println("=== FINANCEIRO DO ALUNO ===");

        System.out.print("Digite o código do aluno: ");
        int codigo = entrada.nextInt();
        entrada.nextLine();

        Aluno aluno = listaAlunos.buscarAluno(codigo);

        if (aluno == null)
        {
            System.out.println("Aluno não encontrado.");
            return;
        }

        // Se ainda não há parcelas, permite cadastrá-las
        if (!aluno.possuiMensalidades())
        {
            System.out.println();
            System.out.println(
                "Nenhuma mensalidade cadastrada para este aluno."
            );

            System.out.print(
                "Deseja cadastrar as mensalidades agora? (S/N): "
            );

            String resposta = entrada.nextLine();

            if (resposta.equalsIgnoreCase("S"))
            {
                System.out.print("Quantidade de parcelas: ");
                int quantidade = entrada.nextInt();

                while (quantidade <= 0)
                {
                    System.out.println(
                        "A quantidade deve ser maior que zero."
                    );

                    System.out.print("Quantidade de parcelas: ");
                    quantidade = entrada.nextInt();
                }

                double[] valores = new double[quantidade];

                for (int i = 0; i < quantidade; i++)
                {
                    do
                    {
                        System.out.print(
                            "Valor da parcela " + (i + 1) + ": R$ "
                        );

                        valores[i] = entrada.nextDouble();

                        if (valores[i] <= 0)
                        {
                            System.out.println(
                                "O valor deve ser maior que zero."
                            );
                        }

                    } while (valores[i] <= 0);
                }

                entrada.nextLine();

                aluno.adicionarMensalidades(valores);

                System.out.println(
                    "Mensalidades cadastradas com sucesso."
                );
            }
        }

        System.out.println();
        aluno.exibirMensalidades();

        if (aluno.possuiMensalidades())
        {
            System.out.println();

            System.out.print(
                "Digite o número da parcela que deseja pagar " +
                "(0 para não pagar): "
            );

            int parcela = entrada.nextInt();
            entrada.nextLine();

            if (parcela > 0)
            {
                aluno.pagarMensalidade(parcela - 1);

                System.out.println();
                aluno.exibirMensalidades();
            }
        }
    }
}