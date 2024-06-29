# CaliopeLib

## Autores
- DIOGO DE LIMA MENEZES
- JOÃO PEDRO CINTRA KARPINSKI
  
## Descrição
A aplicação proposta é um aplicativo móvel de gerenciamento de leituras desenvolvido para a plataforma Android. Este aplicativo permitirá aos usuários cadastrados e não cadastrados gerenciar suas leituras e configurar lembretes diários para leitura. Os usuários cadastrados poderão inserir, editar, visualizar e excluir informações sobre livros, incluindo título, foto, avaliação e descrição. Além disso, poderão criar, editar e excluir alarmes de leitura. A aplicação garantirá a qualidade, confiabilidade e segurança, será desenvolvida em Java utilizando a biblioteca Room para a persistência de dados localmente.

## Requisitos Funcionais
- RF01: O sistema deve permitir que o usuário cadastre informações de um livro, incluindo título (obrigatório), foto (opcional), avaliação (obrigatória) e descrição (opcional).
- RF02: O sistema deve permitir que o usuário programe alarmes diários para leitura, com notificações nos horários especificados.
- RF03: O sistema deve permitir que o usuário crie, edite e exclua alarmes de leitura.
- RF04: O sistema deve permitir que o usuário visualize os livros cadastrados, exibindo títulos, fotos (tiradas ou padrão) e avaliações para cada item listado.
- RF05: O sistema deve permitir que o usuário edite e exclua as informações dos livros cadastrados.
- RF06: O sistema deve permitir que o usuário acesse detalhes de uma leitura cadastrada, incluindo título, imagem (tirada ou padrão), avaliação e descrição (se disponível).
- RF07: O sistema deve permitir a autenticação e registro de novos usuários.
- RF08: O sistema deve permitir a utilização limitada das funcionalidades para usuários não cadastrados. 

## Requisitos Não Funcionais
- RNF01: O sistema deve garantir a qualidade, confiabilidade e segurança da aplicação.
- RNF02: O sistema deve ser compatível com pelo menos 80% dos dispositivos móveis Android, conforme estatísticas fornecidas pelo Android Studio.
- RNF03: O sistema deve ser desenvolvido na linguagem de programação Java.
- RNF04: O sistema deve utilizar a biblioteca Room para persistência de dados localmente no dispositivo do usuário.
- RNF05: O sistema deve armazenar localmente os dados de cadastro (login e senha) e todas as outras informações do aplicativo.

## Recursos 
- Banco de dados (Room) (Para todas as persistências do app)
- Strings (Para armazenar textos estáticos do app)
- Som (Notificações e/ou alarme leitura)
- Notificações (Para lembrete de leitura)
- Alarme (Para lembrete de leitura)
- Câmera (Para fotografar livros)
- Criptografia (para armazenar senhas, em BD local)

