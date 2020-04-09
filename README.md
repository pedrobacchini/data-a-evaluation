# Execute o aplicativo

Run `mvn clean install`

A partir do diretório raiz do projeto. Isso irá gerar um arquivo war no diretório back-end/target. Ele pode ser implantado no servidor Tomcat e o aplicativo pode ser visualizado.

Para executar o aplicativo Spring Boot usando o Maven, execute o seguinte comando no diretório back-end.

Run `mvn spring-boot: run`

Depois que o aplicativo for iniciado, poderemos ver a página de boas-vindas usando http://localhost:8080.

# Detalhamento do projeto

# Público alvo
O aplicativo tem como público alvo pessoas que trabalham dentro de entidades de previdência
complementar e que desejam realizar eleições para preencher cargos de dentro da própria
entidade.
EscopoO escopo abaixo pode ser considerado tanto para as atividades de frontend e backend. Não
precisa se preocupar com uma tela de login/autenticação ou cadastro de usuários. Todas as
telas desenvolvidas podem estar visíveis a todos (telas administrativas e as telas do
eleitor/votante).

# 1.Cadastro da Eleição
Descrição: Função que cadastra uma eleição a ser realizada. Este cadastro deve conter o nome
da eleição e as datas de início e fim dela.
Detalhamento: todos os campos devem ser obrigatórios. Caso algum campo não seja
preenchido, alertar o usuário da obrigatoriedade do preenchimento.

# 2. Cadastro do Cargo
Descrição: Função que cadastra um cargo a ser preenchido pelos candidatos. Este cadastro deve
conter somente o nome do cargo.
Detalhamento: o nome do cargo é obrigatório. Caso o campo não seja preenchido, alertar o
usuário da obrigatoriedade do preenchimento.

# 3.Cadastro do Candidato
Descrição: Função que cadastra um candidato para concorrer a um cargo de uma eleição. Este
cadastro deve conter o nome do candidato. Os cargos cadastrados devem ser exibidos para que
seja selecionado dentro de um combo (select). A tela deve permitir o upload da foto do
candidato.
Detalhamento: todos os campos devem ser obrigatórios. Caso algum campo não seja
preenchido, alertar o usuário da obrigatoriedade do preenchimento. Ao realizar o upload da
foto a mesma deve ser exibida na tela em uma área reservada para este fim.

# 4.Área do eleitor
Descrição: Área que servirá para realizar as votações.
Detalhamento: Como a aplicação proposta não possui autenticação, ao entrar na área do eleitor
serão solicitados o nome e o CPF da pessoa votante. A tela deverá apresentar dados somente se
existir uma eleição cadastrada e a data atual deve estar dentro do intervalo cadastrado na
eleição. A tela deverá apresentar os cargos cadastrados com os respectivos candidatos. A forma
de organizar estas informações fica a critério do desenvolvedor. Pode ser tudo numa mesma
tela ou separadas por diferentes telas. O usuário poderá selecionar apenas um candidato de
cada cargo obrigatoriamente. Ao final o usuário confirmará em um único botão as opções
selecionadas. A aplicação salvará as informações (incluindo o nome e o CPF do votante) e gerará
um número de protocolo alfanumérico de 16 dígitos separados por um traço a cada 4 dígitos. O
protocolo deve ser exibido ao votante e persistido em banco de dados também. O usuário não
poderá mais alterar o voto após confirmar os votos

# 5.Relatórios
Descrição: Função que disponibilizará o resultado parcial e final da eleição.
Detalhamento: O relatório parcial poderá ser emitido a qualquer tempo durante o andamento
das eleições. Após o término e antes do início das eleições o relatório parcial não pode ser
emitido. Já o relatório final poderá ser emitido somente após o término das eleições. O formato
de emissão do relatório pode ser em qualquer formato: arquivo para download CSV, TXT, ou
exibido direto na tela. O relatório parcial deve apresentar somente a quantidade de pessoas que
já votaram. O relatório final deve apresentar a quantidade de pessoas que já votaram bem comoa quantidade de votos que os candidatos receberam, organizados pelo cargo e ordenados pelo
número de votos recebidos.