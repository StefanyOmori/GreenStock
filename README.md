# GreenStock 🌱
<br>
<br>

## 1. Identificação

**Projeto:** GreenStock  
**Integrantes:** 
<br>
Ana Carolina Calheiros de Lima 555316 
<br>
Stefany Miyahara Omori 558792
<br>
Yasmin Sayuri Iechika Muniz 559011
<br>
**Repositório:** https://github.com/StefanyOmori/GreenStock  
**Vídeo pitch:** 

<br>
<br>

## 2. Tema Global Solution: CitizenTech

O GreenStock está relacionado ao tema CitizenTech por utilizar a tecnologia para facilitar o controle de produtos e incentivar o consumo consciente.
O aplicativo ajuda a evitar desperdícios por meio do acompanhamento de quantidades, validades e alertas de estoque.

<br>
<br>

## 3. Descrição da solução

O GreenStock é um aplicativo Android para gerenciamento de estoque. Ele permite consultar produtos, acompanhar quantidades, visualizar alertas e cadastrar novos itens.
Seu objetivo é facilitar a organização e reduzir o desperdício de produtos.

<br>
<br>

## 4. Funcionalidades

- Visualização de indicadores na Dashboard.
- Consulta e pesquisa de produtos.
- Filtro por categoria.
- Cadastro de produtos.
- Visualização dos detalhes dos produtos.
- Alertas de estoque baixo e validade.
- Relatórios com informações do estoque.

<br>
<br>

## 5. Fluxo de navegação

O aplicativo inicia na Splash Screen e verifica se o usuário já concluiu o Onboarding. Depois, direciona para a Dashboard, que permite acessar Estoque, Alertas, Relatórios e Cadastro de Produtos.
Na tela de Estoque, é possível consultar os produtos e abrir seus detalhes.

<br>
<br>

## 6. Telas do aplicativo

Splash Screen: 
Tela inicial do aplicativo.

<img width="263" height="575" alt="Screenshot 2026-11-09 104813" src="https://github.com/user-attachments/assets/44e7175a-01d1-4d4e-8be1-7e6db938d3ed" />


Onboarding: 
Apresentação das funcionalidades.

<img width="303" height="652" alt="Screenshot 2026-11-09 102909" src="https://github.com/user-attachments/assets/56996c73-7301-44f1-b596-71cb037ed452" />
<img width="307" height="655" alt="Screenshot 2026-11-09 102925" src="https://github.com/user-attachments/assets/67928d9f-f8f2-4c63-ab50-0bf8d33828c2" />
<img width="303" height="648" alt="Screenshot 2026-11-09 102947" src="https://github.com/user-attachments/assets/90ad84d6-f8ad-4f74-be49-60ee9e6c2873" />


Dashboard: 
Exibe indicadores do estoque.

<img width="304" height="646" alt="Screenshot 2026-11-09 103005" src="https://github.com/user-attachments/assets/b6fbcd5a-4642-46f6-ba4d-32a9921a4612" />


Estoque: 
Lista e permite pesquisar produtos.

<img width="304" height="652" alt="Screenshot 2026-11-09 103034" src="https://github.com/user-attachments/assets/ac363edf-5069-4b65-99bf-7f16985247ae" />


Cadastro: 
Permite adicionar produtos.

<img width="309" height="651" alt="Screenshot 2026-11-09 103049" src="https://github.com/user-attachments/assets/a76d3001-e381-4e6a-baf9-8e7f7c0278ba" />


Alertas: 
Mostra produtos que precisam de atenção.

<img width="308" height="649" alt="Screenshot 2026-11-09 103121" src="https://github.com/user-attachments/assets/406e1bd9-72ef-40b4-959f-45ed133ebb09" />


Relatórios: 
Apresenta dados do estoque.

<img width="302" height="643" alt="Screenshot 2026-11-09 103137" src="https://github.com/user-attachments/assets/ef4a0c63-7eb3-46ad-83a4-67586264326f" />


Detalhes: Exibe informações do produto.

<img width="320" height="673" alt="Screenshot 2026-11-09 112540" src="https://github.com/user-attachments/assets/811007cc-878a-45e7-bae7-2ebe3b8fb90c" />


<br>
<br>

## 7. API utilizada

O aplicativo utiliza a API **DummyJSON** para buscar produtos da categoria groceries. <br>
**Endpoint:** `https://dummyjson.com/products/category/groceries` <br>
A API fornece informações como nome, categoria, quantidade e imagem dos produtos. A validade não é fornecida pela API.

<br>
<br>

## 8. Arquitetura

O projeto utiliza Kotlin e Jetpack Compose. A organização é dividida em:
- **data:** modelos, API e repositório de produtos.
- **ui:** telas, estados e ViewModels.
- **navigation:** rotas e navegação entre telas.
O Retrofit busca os produtos na API DummyJSON. O SharedPreferences salva a conclusão do Onboarding. Os produtos cadastrados manualmente ficam armazenados em memória durante a execução do aplicativo.

<br>
<br>
