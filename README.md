# ESII-WP-17

# Elementos do grupo:

|      Aluno      | Numero  |
|:---------------:|:-------:|
| Artem Bogdan    | 82847   |
| Beatriz Gomes   | 82195   |
| Catarina Garcez | 82517   |
| José Gonçalves  | 82694   |
| Júlia Monteiro  | 82472   |
| Miguel Mira     | 82966   |

# Instruções passo a passo para a instalação/configuração/execução da aplicação

Vai ser enviado tanto a imagem como o container do WordPress, no entanto as operações realizadas abaixo descritas foram com base no container:

Importar container
zcat g17_wp.gz | docker import - g17_wp
Iniciar container
docker start g17_wp
Entrar no container
docker container exec -it g17_wp bash 


# Funcionalidades não implementadas:


Não foi possível fazer a integração das aplicações Java com o Site WordPress.


**Requisito 1 (José Gonçalves) :**
Ponto g) - não foi possível fazer a integração das aplicações Java com o Site WordPress.
Pontos i) e j) - foram criadas as páginas com plugins, mas não se procedeu a mais alterações.
Ponto a) - Foi criado o user role Member, e os utilizadores admin e member. As passwords são iguais aos usernames.

**Requisito 2 (Júlia Monteiro) :**

Não foi possível aceder ao wordpress para efetuar a monitorização do site, no entanto foi demonstrada a execução de um exemplo de testes do selenium (ponto a), e foi usada a classe Timer para fazer verificação de 2 em 2 horas (ponto b).

**Requisito 3 (Beatriz Gomes) :**



**Requisito 4 (Catarina Garcez):**

A aplicação funciona como é pedido no enunciado. Contudo a integração nao foi conseguida.

**Requisito 5 (Artem Bogdan) :**

Para além da integração com site WordPress, não foi possível colocar a aplicação Java a interagir com o site. Assumi que a informação proveniente vai ser recebida no args [0] do main em forma de string

**Requisito 6 (Miguel Mira):**

Todas as alíneas foram realizadas como era pedido no enunciado. No que diz respeito à alínea c) , os ficheiros foram colocados lado a lado como pedido, mas não foi feito o highlighting das diferenças.
Tal como os requisitos anteriores a integração com o site não foi possível.

