//tela detalhes de produtos
$(function () {
    var parcelamento;
    parcelamento = $(".detalhesProduto").text().replace("R$", "");
    parcelamento = parseFloat(parcelamento);
    parcelamento /= 4;
    parcelamento = parcelamento.toFixed(2);
    $(".detalhesProduto p:nth-of-type(2)").html("4x de R$ " + parcelamento + " sem juros");
});

//tela index
$(function () {
    var parcelamento, numeroDivProduto = 2, numeroDivPreco = 3;

    for (j = 1; j <= numeroDivProduto; j++) {
        for (i = 1; i <= numeroDivPreco; i++) {
            parcelamento = $(".produtos:nth-of-type(" + j + ") div:nth-of-type(" + i + ") p:nth-of-type(2)").text().replace("R$", "");
            parcelamento = parseFloat(parcelamento);
            parcelamento /= 4;
            parcelamento = parcelamento.toFixed(2);
            console.log(parcelamento);
            $(".produtos:nth-of-type(" + j + ") div:nth-of-type(" + i + ") p:nth-of-type(3)").html("4x de R$ " + parcelamento + " sem juros");
        }
    }
});