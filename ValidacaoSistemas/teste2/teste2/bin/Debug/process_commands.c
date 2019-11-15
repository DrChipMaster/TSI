#include "stm32f7xx_hal.h"
#include "process_commands.h"
#include "usart.h"
#include "stdlib.h"
#include "gpio.h"
#include "adc.h"

//Union used to increment memory address
union ui32_to_ui8{
	uint32_t ui32;
	uint8_t ui8[4];
};

//Last Valid Command Buffer
uint8_t lastCommand_Buffer[15];

//saves last valid command
void save_command(){
	uint8_t index_aux = 0;
	
	while(Rx_Buffer[index_aux] != '\0'){
		lastCommand_Buffer[index_aux] = Rx_Buffer[index_aux];
		
		index_aux++;
	}
	lastCommand_Buffer[index_aux] = '\0';
}

//reads from memory - SRAM1
void memory_read(uint32_t addr, uint8_t lengh){
	uint32_t volatile * ptr_addr_MR = (uint32_t *)addr;
	uint8_t index = 0;
	union ui32_to_ui8 u;
	
	printf("Memory Address: %d; Lengh: %d;\r\n", addr, lengh);
	printf("Readed: ");
	
	index = 0;
	while(index != lengh){
		HAL_Delay(1);
		printf("%c", (char)*ptr_addr_MR);	
		u.ui32 = (uint32_t)ptr_addr_MR;
		u.ui8[0]++;
		ptr_addr_MR = (uint32_t*) u.ui32;
		
		index++;
	}
	printf("\r\nMemory Readed Succesfully!\r\n\r\n>");
}

//parsing MR
int process_MR(){
	uint32_t addr = 0;
	uint8_t auxADDR_Buffer[10];
	uint8_t lengh = 0;
	uint8_t auxLENGH_Buffer[10];
	uint8_t index = 0;
	uint8_t index_2 = 0;
	uint8_t Rx_spacebar = 0;
	uint8_t indexSpace_2 = 0;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20){
			Rx_spacebar++;
			indexSpace_2 = index;
		}
		index++;
	}
	
	if(Rx_spacebar == 2){		
		for(int i = 2; i <= indexSpace_2; i++){
			auxADDR_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		addr = atoi((const char *)auxADDR_Buffer);
		addr += 0x20020000;
		
		index_2 = 0;
		for(int i = indexSpace_2; i <= index; i++){
			auxLENGH_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		lengh = atoi((const char *)auxLENGH_Buffer);
		
		if(addr >= 0x20020000 && addr <= 0x2002FFFF && lengh > 0x0 && lengh <= 0xFF ){
			memory_read(addr, lengh);
			
			save_command();
			
			return 1;
		}
		
		else{
			return -1;
		}
	}
	else{
		return -1;
	}
}

//writes on memory - SRAM1
void memory_write(uint32_t addr, uint8_t lengh, uint8_t * auxBYTE_Buffer){
	uint32_t volatile * ptr_addr = (uint32_t *)addr;
	uint8_t index = 0;
	union ui32_to_ui8 u;
	
	printf("Memory Address: %d; Lengh: %d;\r\n", addr, lengh);
	
	index = 0;
	while(index != lengh){
		(*ptr_addr) = auxBYTE_Buffer[index];
		u.ui32 = (uint32_t) ptr_addr;
		u.ui8[0]++;
		ptr_addr = (uint32_t*) u.ui32;
		
		index++;
	}
	printf("Memory Written Succesfully!\r\n\r\n>");
}

//parsing MW
int process_MW(){
	uint32_t addr = 0;
	uint8_t auxADDR_Buffer[10];
	uint8_t lengh = 0;
	uint8_t auxLENGH_Buffer[10];
	uint8_t auxBYTE_Buffer[256];
	uint8_t index = 0;
	uint8_t index_2 = 0;
	uint8_t Rx_spacebar = 0;
	uint8_t indexSpace_3 = 0;
	uint8_t indexSpace_2 = 0;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20){	//gets index space 3
			Rx_spacebar++;
			indexSpace_3 = index;
		}
		index++;
	}
	
	if(Rx_spacebar == 3){	//verifies if there are 3 spaces
		indexSpace_2 = indexSpace_3;
		indexSpace_2--;
		while(Rx_Buffer[indexSpace_2] != 0x20){	//gets index space 2
			indexSpace_2--;
		}
		
		for(int i = 2; i <= indexSpace_2; i++){		//gets addr
			auxADDR_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		addr = atoi((const char *)auxADDR_Buffer);
		addr += 0x20020000;
		
		index_2 = 0;
		for(int i = indexSpace_2; i <= indexSpace_3; i++){	//gets lengh
			auxLENGH_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		lengh = atoi((const char *)auxLENGH_Buffer);
		
		if(addr >= 0x20020000 && addr <= 0x2002FFFF && lengh > 0x0 && lengh <= 0xFF){
			index_2 = 0;
			for(int i = ++indexSpace_3; i <= index; i++){	//gets bytes
				auxBYTE_Buffer[index_2] = Rx_Buffer[i];
				index_2++;
			}
			auxBYTE_Buffer[index_2] = '\0';
			
			memory_write(addr, lengh, auxBYTE_Buffer);
			
			save_command();
			
			return 1;
		}
		
		else{
			return -1;
		}
	}
	else{
		return -1;
	}
}

//config pin as input
void make_input(char port_addr, uint16_t pinsett){
	GPIO_InitTypeDef GPIO_InitStruct;
	GPIO_InitStruct.Mode = GPIO_MODE_INPUT;
	GPIO_InitStruct.Pin = pinsett;
			
	switch(port_addr){
		case ('A'):
			HAL_GPIO_Init(GPIOA, &GPIO_InitStruct);
			break;
		case ('B'):
			HAL_GPIO_Init(GPIOB, &GPIO_InitStruct);
			break;
		case ('C'):
			HAL_GPIO_Init(GPIOC, &GPIO_InitStruct);
			break;
		case ('D'):
			HAL_GPIO_Init(GPIOD, &GPIO_InitStruct);
			break;
		case ('E'):
			HAL_GPIO_Init(GPIOE, &GPIO_InitStruct);
			break;
		case ('F'):
			HAL_GPIO_Init(GPIOF, &GPIO_InitStruct);
			break;
		case ('G'):
			HAL_GPIO_Init(GPIOG, &GPIO_InitStruct);
			break;
		case ('H'):
			HAL_GPIO_Init(GPIOH, &GPIO_InitStruct);
			break;
		case ('I'):
			HAL_GPIO_Init(GPIOI, &GPIO_InitStruct);
			break;
		case ('J'):
			HAL_GPIO_Init(GPIOJ, &GPIO_InitStruct);
			break;
		case ('K'):
			HAL_GPIO_Init(GPIOK, &GPIO_InitStruct);
			break;
		default: 
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
			break;
	}
	printf("Port: %c; Pinsett: %d;\r\n", (char)port_addr, pinsett);
	printf("Make Pin Input Completed!\r\n\r\n>");
}

//parsing MI
int process_MI(){
	char port_addr = 0;
	uint16_t pinsett = 0;
	uint8_t auxPINSETT_Buffer[10];
	uint8_t index = 0;
	uint8_t index_2 = 0;
	uint8_t Rx_spacebar = 0;
	uint8_t indexSpace_2 = 0;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20){
			Rx_spacebar++;
			indexSpace_2 = index;
		}
		index++;
	}
	
	if(Rx_spacebar == 2){		
		port_addr = Rx_Buffer[3];
		
		index_2 = 0;
		for(int i = indexSpace_2; i <= index; i++){
			auxPINSETT_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		pinsett = atoi((const char *)auxPINSETT_Buffer);
		
		if(port_addr >= 'A' && port_addr <= 'K' && pinsett <= 0xFFFF){
			make_input(port_addr, pinsett);
			
			save_command();
			
			return 1;
		}
		
		else{
			return -1;
		}
	}
	else{
		return -1;
	}
}

//config pin as output
void make_output(char port_addr, uint16_t pinsett){
	GPIO_InitTypeDef GPIO_InitStruct;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
	GPIO_InitStruct.Pin = pinsett;
			
	switch(port_addr){
		case ('A'):
			HAL_GPIO_Init(GPIOA, &GPIO_InitStruct);
			break;
		case ('B'):
			HAL_GPIO_Init(GPIOB, &GPIO_InitStruct);
			break;
		case ('C'):
			HAL_GPIO_Init(GPIOC, &GPIO_InitStruct);
			break;
		case ('D'):
			HAL_GPIO_Init(GPIOD, &GPIO_InitStruct);
			break;
		case ('E'):
			HAL_GPIO_Init(GPIOE, &GPIO_InitStruct);
			break;
		case ('F'):
			HAL_GPIO_Init(GPIOF, &GPIO_InitStruct);
			break;
		case ('G'):
			HAL_GPIO_Init(GPIOG, &GPIO_InitStruct);
			break;
		case ('H'):
			HAL_GPIO_Init(GPIOH, &GPIO_InitStruct);
			break;
		case ('I'):
			HAL_GPIO_Init(GPIOI, &GPIO_InitStruct);
			break;
		case ('J'):
			HAL_GPIO_Init(GPIOJ, &GPIO_InitStruct);
			break;
		case ('K'):
			HAL_GPIO_Init(GPIOK, &GPIO_InitStruct);
			break;
		default: 
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
			break;
	}
	printf("Port: %c; Pinsett: %d;\r\n", (char)port_addr, pinsett);
	printf("Make Pin Onput Completed!\r\n\r\n>");
}

//parsing MO
int process_MO(){
	char port_addr = 0;
	uint16_t pinsett = 0;
	uint8_t auxPINSETT_Buffer[10];
	uint8_t index = 0;
	uint8_t index_2 = 0;
	uint8_t Rx_spacebar = 0;
	uint8_t indexSpace_2 = 0;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20){
			Rx_spacebar++;
			indexSpace_2 = index;
		}
		index++;
	}
	
	if(Rx_spacebar == 2){		
		port_addr = Rx_Buffer[3];
		
		index_2 = 0;
		for(int i = indexSpace_2; i <= index; i++){
			auxPINSETT_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		pinsett = atoi((const char *)auxPINSETT_Buffer);
		
		if(port_addr >= 'A' && port_addr <= 'K' && pinsett <= 0xFFFF){
			make_output(port_addr, pinsett);
			
			save_command();
				
			return 1;
		}
		
		else{
			return -1;
		}
	}
	else{
		return -1;
	}
}

//reads digital pin input value
void read_digital(char port_addr, uint16_t pinsett){
	uint8_t read;
	
	switch(port_addr){
		case ('A'):
			read = HAL_GPIO_ReadPin(GPIOA, pinsett);
			break;
		case ('B'):
			read = HAL_GPIO_ReadPin(GPIOB, pinsett);
			break;
		case ('C'):
			read = HAL_GPIO_ReadPin(GPIOC, pinsett);
			break;
		case ('D'):
			read = HAL_GPIO_ReadPin(GPIOD, pinsett);
			break;
		case ('E'):
			read = HAL_GPIO_ReadPin(GPIOE, pinsett);
			break;
		case ('F'):
			read = HAL_GPIO_ReadPin(GPIOF, pinsett);
			break;
		case ('G'):
			read = HAL_GPIO_ReadPin(GPIOG, pinsett);
			break;
		case ('H'):
			read = HAL_GPIO_ReadPin(GPIOH, pinsett);
			break;
		case ('I'):
			read = HAL_GPIO_ReadPin(GPIOI, pinsett);
			break;
		case ('J'):
			read = HAL_GPIO_ReadPin(GPIOJ, pinsett);
			break;
		case ('K'):
			read = HAL_GPIO_ReadPin(GPIOK, pinsett);
			break;
		default: 
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
			break;
	}
	printf("Port: %c; Pinsett: %d;\r\n", (char)port_addr, pinsett);
	printf("Readed: %d\r\n", read);
	printf("Read Dig Input Completed!\r\n\r\n>");
}

//parsing RD
int process_RD(){
	char port_addr = 0;
	uint16_t pinsett = 0;
	uint8_t auxPINSETT_Buffer[10];
	uint8_t index = 0;
	uint8_t index_2 = 0;
	uint8_t Rx_spacebar = 0;
	uint8_t indexSpace = 0;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20){
			Rx_spacebar++;
			indexSpace = index;
		}
		index++;
	}
	
	if(Rx_spacebar == 2){		
		port_addr = Rx_Buffer[3];
		
		index_2 = 0;
		for(int i = indexSpace; i <= index; i++){
			auxPINSETT_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		pinsett = atoi((const char *)auxPINSETT_Buffer);
		
		if(port_addr >= 'A' && port_addr <= 'K' && pinsett <= 0xFFFF){
			read_digital(port_addr, pinsett);
			
			save_command();
				
			return 1;
		}
		
		else{
			return -1;
		}
	}
	else{
		return -1;
	}
}

//writes digital pin output value
void write_digital(char port_addr, uint16_t pinsett, uint16_t byte){
	switch(port_addr){
		case ('A'):
			HAL_GPIO_WritePin(GPIOA, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOA, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('B'):
			HAL_GPIO_WritePin(GPIOB, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOB, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('C'):
			HAL_GPIO_WritePin(GPIOC, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOC, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('D'):
			HAL_GPIO_WritePin(GPIOD, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOD, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('E'):
			HAL_GPIO_WritePin(GPIOE, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOE, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('F'):
			HAL_GPIO_WritePin(GPIOF, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOF, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('G'):
			HAL_GPIO_WritePin(GPIOG, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOG, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('H'):
			HAL_GPIO_WritePin(GPIOH, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOH, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('I'):
			HAL_GPIO_WritePin(GPIOI, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOI, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('J'):
			HAL_GPIO_WritePin(GPIOJ, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOJ, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		case ('K'):
			HAL_GPIO_WritePin(GPIOK, (byte & pinsett), GPIO_PIN_SET);
			HAL_GPIO_WritePin(GPIOK, ~(byte & pinsett), GPIO_PIN_RESET);
			break;
		default: 
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
			break;
	}
	printf("Port: %c; Pinsett: %d; Byte: %d;\r\n", (char)port_addr, pinsett, byte);
	printf("Write Dig Output Completed!\r\n\r\n>");
}

//parsing WD
int process_WD(){
	char port_addr;
	uint16_t pinsett;
	uint8_t auxPINSETT_Buffer[10];
	uint8_t auxBYTE_WD_Buffer[10];
	uint16_t byte;
	uint8_t index = 0;
	uint8_t index_2 = 0;
	uint8_t Rx_spacebar = 0;
	uint8_t indexSpace_3 = 0;
	uint8_t indexSpace_2 = 0;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20){	//gets index space 3
			Rx_spacebar++;
			indexSpace_3 = index;
		}
		index++;
	}
	
	if(Rx_spacebar == 3){	//verifies if there are 1 spaces and 2 points
		indexSpace_2 = indexSpace_3;
		indexSpace_2--;
		while(Rx_Buffer[indexSpace_2] != 0x20){	//gets index space 2
			indexSpace_2--;
		}
		
		port_addr = Rx_Buffer[3];
		
		index_2 = 0;
		for(int i = indexSpace_2; i <= indexSpace_3; i++){	//gets pinsett
			auxPINSETT_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		pinsett = atoi((const char *)auxPINSETT_Buffer);
		
		if(port_addr >= 'A' && port_addr <= 'K' && pinsett <= 0xFFFF ){
			
			index_2 = 0;
			for(int i = ++indexSpace_3; i <= index; i++){	//gets bytes
				auxBYTE_WD_Buffer[index_2] = Rx_Buffer[i];
				index_2++;
			}
			auxBYTE_WD_Buffer[index_2] = '\0';
			byte = atoi((const char *)auxBYTE_WD_Buffer);
			
			if(byte <= 0xFFFF){
				write_digital(port_addr, pinsett, byte);
				
				save_command();
				
				return 1;
			}
			else{
				return -1;
			}
		}
		else{
			return -1;
		}
	}
	else{
		return -1;
	}
}

//config & select channel from ADC
void read_analog(char addr3){
	ADC_ChannelConfTypeDef sConfig;
	
	sConfig.Rank = ADC_REGULAR_RANK_1;
  sConfig.SamplingTime = ADC_SAMPLETIME_3CYCLES;
	
	switch(addr3){
		case ('A'):
			sConfig.Channel = ADC_CHANNEL_0;
			break;
		case ('B'):
			sConfig.Channel = ADC_CHANNEL_1;
			break;
		case ('C'):
			sConfig.Channel = ADC_CHANNEL_2;
			break;
		case ('D'):
			sConfig.Channel = ADC_CHANNEL_3;
			break;
		case ('E'):
			sConfig.Channel = ADC_CHANNEL_4;
			break;
		case ('F'):
			sConfig.Channel = ADC_CHANNEL_5;
			break;
		case ('G'):
			sConfig.Channel = ADC_CHANNEL_6;
			break;
		case ('H'):
			sConfig.Channel = ADC_CHANNEL_7;
			break;
		case ('I'):
			sConfig.Channel = ADC_CHANNEL_8;
			break;
		case ('J'):
			sConfig.Channel = ADC_CHANNEL_9;
			break;
		case ('K'):
			sConfig.Channel = ADC_CHANNEL_10;
			break;
		case ('L'):
			sConfig.Channel = ADC_CHANNEL_11;
			break;
		case ('M'):
			sConfig.Channel = ADC_CHANNEL_12;
			break;
		case ('N'):
			sConfig.Channel = ADC_CHANNEL_13;
			break;
		case ('O'):
			sConfig.Channel = ADC_CHANNEL_14;
			break;
		case ('P'):
			sConfig.Channel = ADC_CHANNEL_15;
			break;
		default: 
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
			break;
	}
	if (HAL_ADC_ConfigChannel(&hadc1, &sConfig) != HAL_OK)
  {
    _Error_Handler(__FILE__, __LINE__);
  }
	
	printf(" Channel Number: %d;\r\n", sConfig.Channel);
}

//parsing RA
int process_RA(){
	uint8_t index = 0;
	uint8_t spacebar = 0;
	char addr3;
	double adc_voltage;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20)
			spacebar++;
		
		index++;
	}
	
	if(spacebar == 1){
		addr3 = Rx_Buffer[3];
		printf("Channel: %c;", (char)addr3);
		
		if(addr3 >= 'A' && addr3 <= 'P'){
			read_analog(addr3);
			
			HAL_ADC_Start_IT(&hadc1);
			HAL_Delay(1);
			
			if(adc1Flag != 0){
				adc1Flag = 0;
				adc_voltage = ((((double) adcValue /4095) * 3300) / 1000);
				printf("ADC Value: %hu\r\n", adcValue);
				printf("ADC Input Voltage: %0.2f V\r\n", adc_voltage);
				printf("Make Pin Onput Completed!\r\n\r\n>");
				
				save_command();
				
				return 1;
			}
			else{
				return -1;
			}
		}
		else{
				return -1;
		}
	}
	else{
		return -1;
	}
}

//chooses sampling period
void sampling_period(uint32_t timeunit, uint8_t units){
	
}

//parsing SP
int process_SP(){
	uint32_t timeunit = 0;
	uint8_t auxADDR_Buffer[10];
	uint8_t units = 0;
	uint8_t auxLENGH_Buffer[10];
	uint8_t index = 0;
	uint8_t index_2 = 0;
	uint8_t Rx_spacebar = 0;
	uint8_t indexSpace_2 = 0;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20){
			Rx_spacebar++;
			indexSpace_2 = index;
		}
		index++;
	}
	
	if(Rx_spacebar == 2){		
		for(int i = 2; i <= indexSpace_2; i++){
			auxADDR_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		timeunit = atoi((const char *)auxADDR_Buffer);
		
		index_2 = 0;
		for(int i = indexSpace_2; i <= index; i++){
			auxLENGH_Buffer[index_2] = Rx_Buffer[i];
			index_2++;
		}
		units = atoi((const char *)auxLENGH_Buffer);
		
		if(timeunit <= 1000 && units > 0x0 && units <= 0xFF ){
			sampling_period(timeunit, units);
			
			save_command();
			
			return 1;
		}
		
		else{
			return -1;
		}
	}
	else{
		return -1;
	}
}

//choose ADC channel
int process_AC(){
	uint8_t index = 0;
	uint8_t spacebar = 0;
	char addr3;
	
	while(Rx_Buffer[index] != '\0'){
		if(Rx_Buffer[index] == 0x20)
			spacebar++;
		
		index++;
	}
	
	if(spacebar == 1){
		addr3 = Rx_Buffer[3];
		printf("Channel: %c;", (char)addr3);
		
		if(addr3 >= 'A' && addr3 <= 'P'){
			read_analog(addr3);
			printf(">");
				
			save_command();
				
			return 1;
		}
		else{
			return -1;
		}
	}
	else{
		return -1;
	}
}

//filter on
void process_FN(){
	
}

//filter off
void process_FF(){
	
}

void process_S(){
	
}



//analyses command received
void process_string(){
	if(Rx_Buffer[0] == 'M' && Rx_Buffer[1] == 'R' && Rx_Buffer[2] == 0x20){	
		printf("STM32:    MR - Verifying Parameters...\r\n");
		
		if(process_MR() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'M' && Rx_Buffer[1] == 'W' && Rx_Buffer[2] == 0x20){
		printf("STM32:    MW - Verifying Parameters...\r\n");
		
		if(process_MW() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'M' && Rx_Buffer[1] == 'I' && Rx_Buffer[2] == 0x20 && Rx_Buffer[4] == 0x20){
		printf("STM32:    MI - Verifying Parameters...\r\n");	
		
		if(process_MI() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'M' && Rx_Buffer[1] == 'O' && Rx_Buffer[2] == 0x20 && Rx_Buffer[4] == 0x20){
		printf("STM32:    MO - Verifying Parameters...\r\n");
			
		if(process_MO() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'R' && Rx_Buffer[1] == 'D' && Rx_Buffer[2] == 0x20 && Rx_Buffer[4] == 0x20){
		printf("STM32:    RD - Verifying Parameters...\r\n");		
		
		if(process_RD() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'W' && Rx_Buffer[1] == 'D' && Rx_Buffer[2] == 0x20 && Rx_Buffer[4] == 0x20){
		printf("STM32:    WD - Verifying Parameters...\r\n");		
		
		if(process_WD() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'R' && Rx_Buffer[1] == 'A' && Rx_Buffer[2] == 0x20 && Rx_Buffer[4] == '\0'){
		printf("STM32:    RA - Verifying Parameters...\r\n");
		
		if(process_RA() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'S' && Rx_Buffer[1] == 'P' && Rx_Buffer[2] == 0x20){
		printf("STM32:    SP - Verifying Parameters...\r\n");		
		
		if(process_SP() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'A' && Rx_Buffer[1] == 'C' && Rx_Buffer[2] == 0x20 && Rx_Buffer[4] == '\0'){
		printf("STM32:    AC - Verifying Parameters...\r\n");
		
		if(process_AC() == -1){
			printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
		}
	}
	else if(Rx_Buffer[0] == 'F' && Rx_Buffer[1] == 'N' && Rx_Buffer[2] == '\0'){
		printf("STM32:    Filter ON\r\n");
		
		process_FN();
	}
	else if(Rx_Buffer[0] == 'F' && Rx_Buffer[1] == 'F' && Rx_Buffer[2] == '\0'){
		printf("STM32:    Filter OFF\r\n");
		
		process_FF();
	}
	else if(Rx_Buffer[0] == 'S' && Rx_Buffer[1] == '\0'){
		printf("STM32:    Filter OFF\r\n");
		
		process_S();
	}
	else if(Rx_Buffer[0] == 'V' && Rx_Buffer[1] == 'E' && Rx_Buffer[2] == 'R' && Rx_Buffer[3] == '\0'){
		printf("STM32:    v1.7 GRUPO 9 TURNO 2 LPI II MIEEIC UM 2018/2019\r\n\r\n>");	
	}
	else if(Rx_Buffer[0] == 0x3F && Rx_Buffer[1] == '\0'){	//HELP
		printf("STM32:    LIST OF COMMANDS AVAILABLE:\r\nMR - Memory Read\r\nMW - Memory Write\r\nMI - Memory port pin Input\r\nMO - Make port pin Output\r\nRD - Read Digital Input\r\nWD - Write Digital Ouput\r\nRA - Read Analog\r\n? - HELP\r\nVER - Program Version\r\n<BCKSP> - Delete Character\r\n<ESC> - Delete All Caracters\r\n$ - <ESC> + Execute Last Valid Command\r\n\r\n>");
	}
	
	else{
		printf("STM32:    INVALID COMMAND!!\r\n\r\n>");
	}
}	

//saves command in Buffer
void process_dollar(){
	uint8_t index_aux = 0;
	
	index_aux = 0;
	while(lastCommand_Buffer[index_aux] != '\0'){
		Rx_Buffer[index_aux] = lastCommand_Buffer[index_aux];
		
		index_aux++;
	}
	Rx_Buffer[index_aux] = '\0';
	
	process_string();
}
