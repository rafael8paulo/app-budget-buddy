class StringUtil {

    static formatarDataBrasileiro(dataISO) {
        return new Date(dataISO).toLocaleDateString();
    }

    static formatarDinheiroBrasileiro(value) {
        return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
    }

    static strToNubemDecimal(str) {
        let withoutComma = str.replace(/\./g, '');
        let withDot = withoutComma.replace(',', '.');
        let decimalNumber = parseFloat(withDot);
        return decimalNumber;
    }

}

export default StringUtil;