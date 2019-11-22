#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "lista.h"
#include "rs232.h"
#include "files.h"
#include "serial_com.h"

int main()
{
	char aux_string[NAME_SIZE] = { 0 };
	char name[NAME_SIZE] = { 0 };
	char nome[NAME_SIZE] = { 0 };
	char new_name[NAME_SIZE] = { 0 };
	char empty[NAME_SIZE] = { 0 };
	char loc[NAME_SIZE] = { 0 };
	char aux_str[100] = { 0 };
	char aux_str1[100] = { 0 };
	int password = 8051;
	int i;
	struct node  *aux_station, *aux_station1, *station;
	struct vector list;
	struct station fav[3] = { 0 };
	FILE *arq;
	arq = fopen("History.dat", "a+");
	list.head = NULL;
	unsigned int action = 0, aux = 0, aux1 = 0, aux2 = 0, aux3 = 0, aux4 = 0, aux_load = 0, aux_change = 0;
	float wind_speed = 0, wind_heading = 0, temp = 0, humidity = 0;

	printf(" \n Stations in file:\n\n"); load_list(&list);                                               //Mostra ao iniciar o programa as estações existentes
	if (list.head != NULL) { ordenar_alfabeticamente(&list); print_list(list); }                         //se a lista não está vazia, ordena as stations e print
	else { print_list(list); }                                                                           //se está vazia imprime null apenas

	while (1)
	{
		aux = 0, aux1 = 0, aux2 = 0, aux3 = 0, aux4 = 0, action = 0, aux_change = 0;

		printf(" \n What you wanna do?\n Search:1  Print:2  Insert:3  Remove:4  Change:5  Save:6  Help:7  Comand_History:8  Highlights:9  Fav:10  Access:11  End:0 \n"); gets(aux_str);
		if (sscanf(aux_str, "%d", &action) != 1) { printf(" \n Try again, between (0-11).\n"); }
		else if (action == 0) {                                                                          //quando quer encerrar programa pergunta se quer gravar as stations no ficheiro
			printf(" \n Want end program without saving? (y-YES) (other-NO)\n");
			scanf(" %[^\n]s", aux_string);
			aux = strcmp(aux_string, "y");                                                               //se o 'y' for pressionado sai do programa sem guardar em ficheiro
			fprintf(arq, " Program ended\n");                                                            //guarda no ficheiro do histórico que o programa acabou
			if (aux == 0) { return 0; }
			else { save_list(&list); printf(" \n Stations were saved.\n"); return 0; }                   //se qualquer outra tecla for pressionada guarda as stations no ficheiro
			fclose(arq);
		}
		else if (action < 0 || action > 11) { printf(" \n Try again, between (0-11).\n"); }                //pede para introduzir novamente se o numero inserido não estiver entre 0 e 9

		switch (action) {

		case 1:
			if (list.head == NULL) { printf(" \n Can't search, list is empty.\n"); break; }              //se a lista estiver vazia não é possivel procurar uma station e sai do case 1
			else if (list.head != NULL)
			{
				ordenar_alfabeticamente(&list);
				aux1 = num_stations(&list);                                                              //aux1 fica com o valor do numero de station na lista
				do {
					while (1)                                                                            //fica no ciclo enquanto que o digito escolhido não está dentro do estabelecido no programa (0,1,2,3...)
					{
						printf(" \n How many stations you want to search? (0-%d)\n", aux1); gets(aux_str);                //pede quantas stations quer procurar
						if (sscanf(aux_str, "%d", &aux) != 1) { printf(" \n Try again, between (0-%d).\n", aux1); }       //da erro se forem inseridas letras
						else break;
					} if (aux == 0) { break; }
					if (aux > aux1) { printf(" \n Try again, between (0-%d).\n", aux1); }                //da erro se o numero inserido não estiver nos limites indicados
				} while (aux > aux1); if (aux == 0) { break; }

				do {
					if (aux < aux1)                                                                      //se o numero de stations que pretende procurar é menor que o n que tem la,vai pedindo os nomes ate aux ser igual a 0
					{
						printf(" \n Name Station?\n");                                                   // pede o nome da station que quer procurar
						scanf(" %[^\n]s", name);
						aux_station = search(&list, name); gets(aux_str);
						if (aux_station != NULL) {                                                       //se a station não for null
							ordenar_alfabeticamente(&list);

							fprintf(arq, " Station searched: %s \n", aux_station->info.name);
							print_values(aux_station);
						}
						else if (aux_station == NULL) { printf(" \n There's no station with this name.\n"); }              //se retornar null é porque a station introduzida não existe
						else return 0;
						aux--;
					}
					else {                                                                               //se o numero que quer procurar é igual ao n de stations que tem na lista imprime tudo de uma vez só
						aux_station = list.head;
						do {
							fprintf(arq, " Station searched: %s \n", aux_station->info.name);
							print_values(aux_station);
							aux_station = aux_station->next;                                             //passa para a station seguinte para imprimir os valores da proxima
							aux1--;
						} while (aux1 != 0);                                                             //imprime as estações ate o aux1(contem o numero de stations que existem) for 0
						break;
					}
				} while (aux != 0);
				break;
			}
			break;

		case 2:
			printf(" \n List:\n\n");
			if (list.head == NULL) { print_list(list); }                                                //se não exitir nenhuma station na lista, imprime que a lista está vazia
			else if (list.head != NULL) {
				ordenar_alfabeticamente(&list); print_list(list);                                       //se existir list, ordena as stations existentes e faz print dos nomes das stations

				fprintf(arq, " Printed stations \n");                                                   //guarda no ficheiro do histórico que a lista foi imprimida
			}
			break;

		case 3:
			aux1 = num_stations(&list);                                                                //aux1 fica com o valor do numero de stations na lista
			while (1)                                                                                  //fica no ciclo enquanto não for inserido um digito >1
			{
				printf(" \n How many stations you want to insert?\n"); gets(aux_str);                  //pergunta quantas stations quer inserir
				if (sscanf(aux_str, "%d", &aux) != 1) { printf(" \n Incorrect, try again.\n"); }       //da erro se forem inseridas letras
				else break;
			}
			if (aux == 0) { break; }                                                                   //se não quiser inserir nenhuma, sai do case 3
			while (aux != 0)                                                                           //para cada station nova a inserir pede o nome, a localidade e valores
			{
				do {
					printf(" \n Name Station?\n");                                                     //pede para inserir o nome da station nova 
					scanf(" %[^\n]s", name);

					if (list.head != NULL) {                                                           //só procura o nome introduzido na lista se esta não estiver vazia 
						aux_station1 = search(&list, name);                                            //procura na lista se o novo nome já existe
						if (aux_station1 != NULL) { printf(" \n That name already exist in list, try again.\n"); }      //se existir volta a pedir um nome
					}
					else { break; }
				} while (aux_station1 != NULL);

				fprintf(arq, " Station inserted: %s\n", name);
				printf(" \n Location of this Station?\n");                                             //pede a localidade da station nova
				scanf(" %[^\n]s", loc); insert_list(&list, name, loc);
				printf(" \n Want to set valeus to the station? (y-YES) (other-NO)\n");                 //pergunta se quer introduzir os valores na estação, caso não queria esses valores ficam todos a 0
				scanf(" %[^\n]s", aux_string); aux1 = strcmp(aux_string, "y");
				if (aux1 == 0) { put_valeus(&list, temp, humidity, wind_speed, wind_heading); }        //chama pela função put_valeus para dar set aos valores na station
				else { gets(aux_str); }
				aux--;
			}
			break;

		case 4:
			if (list.head == NULL) { printf(" \n Can't remove, list is empty.\n"); break; }            //se a lista está vazia não pode remover e sai do case 4
			else if (list.head != NULL)
			{
				aux1 = num_stations(&list);                                                            //aux1 fica com o valor do numero de stations na lista
				do {
					while (1)                                                                          //fica no ciclo enquanto não for inserido um digito >1
					{
						printf(" \n How many stations you want to remove? (0-%d)\n", aux1); gets(aux_str);             //pede quantas stations quer remover
						if (sscanf(aux_str, "%d", &aux) != 1) { printf(" \n Try again, between (0-%d).\n", aux1); }    //da erro se forem introduzidas letras
						else { break; }
					} if (aux == 0) { break; }                                                         //se não quiser remover nenhuma station, sai do case 4
					if (aux > aux1) { printf(" \n Try again, between (0-%d).\n", aux1); }              //da erro se o numero inserido não estiver no intervalo indicado
				} while (aux > aux1); if (aux == 0) { break; }


				do {
					if (aux < aux1)                                                                    //se o numero de stations q pretende remover é menor q o n que tem la,vai pedindo os nomes ate aux ser igual a 0
					{
						printf(" \n Station to remove?\n");                                            //pede o nome da station que quer remover
						scanf(" %[^\n]s", name); gets(aux_str);
						if (remove_node(&list, name) == 1) {                                           //chama a função remover e se esta retornar 1
							fprintf(arq, " Station removed: %s\n", name);                              //guarda no ficheiro do histórico
							printf(" \n Station was removed.\n");                                      //imprime que a função foi removida
						}
						else printf(" \n There's no station with this name.\n");                       //se retornar 0, é porque não existe station com o nome inserido
						aux--;                                                                         //decrementa o numero que foi inserido no inicio, até 0
					}
					else                                                                               //se o numero que pretende remover é igual ao numero de stations existentes na lista
					{                                                                                  //remove todas as stations de uma só vez, sem pedir os nomes
						fprintf(arq, " All stations removed\n");                                       //guarda no ficheiro do historico que todas as stations foram removidas
						aux_station = list.head;
						do {
							remove_node(&list, aux_station->info.name);                                //remove a primeira station na lista 
							aux_station = aux_station->next;                                           //passa para a proxima station na lista
						} while (aux_station != NULL);
						printf(" \n Stations were removed.\n"); break;
					}
				} while (aux != 0);
			}
			break;

		case 5:
			if (list.head == NULL) { printf(" \n Can't change, list is empty.\n"); break; }            //se a lista está vazia não pode alterar e sai do case 5
			else if (list.head != NULL)
			{
				aux1 = num_stations(&list);                                                            //aux1 fica com o valor do numero de stations na lista
				do {
					while (1)                                                                          //fica no ciclo enquanto não for inserido um digito >1
					{
						printf(" \n How many stations you want to change? (0-%d)\n", aux1); gets(aux_str);
						if (sscanf(aux_str, "%d", &aux) != 1) { fprintf(stderr, " \n Try again, between (0-%d).\n", aux1); }
						else break;
					} if (aux == 0) { break; }
					if (aux > aux1) { printf(" \n Try again, between (0-%d).\n", aux1); }
				} while (aux > aux1); if (aux == 0) { break; }
				if (aux1 == 1 && aux == 1) { aux3 = 1; aux_station = search(&list, list.head->info.name); }            //se só existe uma station e pretende alterar uma station, não pede o nome porque é obvio que quer alterar a unica que está na lista

				do {
					if (aux3 != 1)
					{
						printf(" \n Station to change?\n");                                            //pede o nome da station que quer alterar          
						scanf(" %[^\n]s", name);  aux_station = search(&list, name);                   //procura na lista se existe alguma station com o nome introduzido
					}
					if (aux_station == NULL) { printf(" \n There's no station with this name.\n"); }   //da erro se não existir nenhuma station com o nome introduzido
					else
					{
						printf(" \n Are you sure, you want to change the name of this station? (y-YES) (other-NO)\n");  //pergunta se tem a certeza que quer alterar esta station
						scanf(" %[^\n]s", aux_string);
						aux2 = strcmp(aux_string, "y");

						if (aux2 == 0)                                                                 //se sim ('y') pede um novo nome que não exista na lista
						{
							do {
								printf(" \n New name?\n");                                             //pede o novo nome
								scanf(" %[^\n]s", new_name);
								aux_station1 = search(&list, new_name);                                //procura na lista se esse novo nome já existe
								if (aux_station1 != NULL) { printf(" \n Name already exist in list, try again.\n"); }   //da erro se o nome introduzido existir
							} while (aux_station1 != NULL);
							aux_change = aux_ask(list, aux_station, new_name);                         //chama a aux_ask para pedir novo nome da localidade e se quer alterar valores 
						}
						else if (aux2 != 0) {
							if (aux3 == 1) { aux_change = aux_ask(list, aux_station, list.head->info.name); }           //se aux3 = 1 quer dizer que só tem uma estaçao e não pede o nome que quer alterar mas pede os novos dados
							else { aux_change = aux_ask(list, aux_station, name); }
						}
					}
					aux--;
				} while (aux != 0);                                                                    //fica neste ciclo ate serem alteradas quantas station foram escolhidas no inicio
			}
			if (aux_change != 1) { gets(aux_str); }
			else  if (aux_change == 1) { break; }
			break;

		case 6:
			fprintf(arq, " Station saved\n");                                                          //guarda no ficheiro do histórico que todas as stations foram guardadas
			save_list(&list); printf(" \n Stations were saved.\n");                                    //guarda no ficheiro a lista
			break;

		case 7:
			while (1)                                                                                  //fica no ciclo enquanto não for inserido um digito >1
			{                                                                                          //mostra ao utilizador as funcionalidades do programa
				printf(" \n What you wanna know? (0-9)/(0 - End)\n"); gets(aux_str);                   //pede qual funcionalidade o utilizador pretende saber mais
				if (sscanf(aux_str, "%d", &aux4) != 1) { printf(" \n Try again, between (0-10).\n"); }  //da erro se o numero introduzido não estiver no intervalo indicado
				else if (aux4 == 0) { break; }
				else if (aux4 < 0 || aux4 > 10 || aux4 == 7) { printf(" \n Try again, between (0-10).\n"); }
				switch (aux4) {
				case 1:    //mais informação sobre a funcionalidade: search
					printf("Search:\n Prints the information of the stations that are in the list.\n This command is available if the list has the station you are looking for.\n");
					break;
				case 2:   //mais informação sobre a funcionalidade: print
					printf("Print:\n Prints all the stations that are in the list.\n");
					break;
				case 3:   //mais informação sobre a funcionalidade: insert
					printf("Insert:\n Allows to insert stations in the list and their information as name ,location and values.\n This command is available if the list has not the station with same name.\n");
					break;
				case 4:   //mais informação sobre a funcionalidade: remove
					printf("Remove:\n Allows to remove stations of the list.\n This command is available if the list has the station you want remove.\n");
					break;
				case 5:  //mais informação sobre a funcionalidade: change
					printf("Change:\n Allows to change stations of the list.\n Name, location and values ??can be change if you want.\n This command is available if the list has the station you want change.\n");
					break;
				case 6:  //mais informação sobre a funcionalidade: save
					printf("Save:\n Allows to save the stations of the list in the file.\n This command is always available. If you do not save, you will lose your progress since the last save.\n");
					break;
				case 8:  //mais informação sobre a funcionalidade: command_history
					printf("Comand_History:\n Command restricted to supervisors.\n");
					break;
				case 9:  //mais informação sobre a funcionalidade: Highlights
					printf("Highlights:\n This command shows the highlights that can be on the stations in the list.\n");
					break;

				case 10: //mais informação sobre a funcionalidade: Favorites
					printf("Favorites:\n Allows you to set the most important stations as favorites so that control over them is more practical.\n Only allowed to have 3 stations as favorites.\n");
					break;


				}
			}
			break;

		case 8:
			printf("\nInsert the password: "); gets(aux_str1);                                         //opçao de ver o historico apenas possivel para quem sabe a password
			if (sscanf(aux_str1, "%d", &action) != 1 || action != password) { printf(" \n Password incorrect.\n"); }    //da erro se a password for incorreta
			else {
				fclose(arq);
				history();
			}
			break;

		case 9:
			i = 0;
			station = list.head;
			printf(" \n Highlights:\n\n");
			if (list.head == NULL) { printf("Nothing to check.\n"); }                                   //se a lista estiver vazia, não há highlights
			else if (list.head != NULL) {
				highlights_list(list);
				do {
					if (strcmp(empty, fav[i].name) != 0) {                   //se houver estaçoes nos favoritos vai verificar se houve alteraçoes nelas
						aux_station = search(&list, fav[i].name);
						if (aux_station == NULL || strcmp(aux_station->info.location, fav[i].location) != 0) {
							printf("Station %s was changed\n", fav[i].name); aux = 1;          //caso nome/loc tenham sido alterados ele avisa
						}
						else if (aux_station->info.temp != fav[i].temp || aux_station->info.humidity != fav[i].humidity || aux_station->info.wind_speed != fav[i].wind_speed || aux_station->info.wind_heading != fav[i].wind_heading) {
							printf("Values of station %s was changed\n", fav[i].name);   //SÓ se nome e loc nao tiverem sido alterados é que vem verificar se os valores estao iguais
						}
					}
					i++;
					station = station->next;
				} while (station != NULL && i <= 2);

				fprintf(arq, " Verified highlights \n");
			}                                                                              //se existir lista, chama a função dos highlights
			break;


		case 10:
			i = 0;

			if (strcmp(empty, fav[i].name) == 0) {                                  //qd nome = 0 significa que nao ha estaçoes nos favoritos de outra forma seria diferente de 0
				printf("\nNothing in Favorites.\n");
			}
			else {
				do {
					aux_station1 = search(&list, fav[i].name);            //procura pela estação para ter a certeza que ainda existe na lista
					if (aux_station1 == NULL) {
						printf("\nStation %s changed.\n", fav[i].name);  //caso o nome de uma estação nos favoritos tenha sido alterado

					}
					else {
						printf(" \n  Name: %s \n", aux_station1->info.name);
						print_values(aux_station1);
					}
					i++;
				} while (strcmp(empty, fav[i].name) != 0 && i <= 2);           //só imprime se houver estaçoes 
			}
			if (list.head == NULL) { printf("Can't insert , list empty.\n"); break; }
			else if (i != 3) {
				printf("\n Want to set some station as favorite ? (y - YES) (other - NO)\n");
				scanf(" %[^\n]s", aux_string); aux1 = strcmp(aux_string, "y");
				if (aux1 == 0) {

					printf("\nName: ");
					scanf(" %[^\n]s", nome);
					aux_station = search(&list, nome);       //para saber se ela existe na lista
					if (aux_station != NULL) {
						i = 0;
						do {
							aux = strcmp(nome, fav[i].name);            //para saber se ja existe nos favoritos
							if (aux == 0) {
								printf("\nStation already exist.\n");
								break;
							}
							i++;
						} while (i < 2);

						if (aux != 0) {                                 //aux != 0 quando nao ha nos favoritos outra estação igual
							i = 0;
							do {

								if (strcmp(empty, fav[i].name) == 0) {            //novamente atraves do nome para saber se ha "espaço" para mais uma estação 

									strcpy(fav[i].name, aux_station->info.name);
									strcpy(fav[i].location, aux_station->info.location);        //insere nos favoritos as caracteristicas da estação que inseriu
									fav[i].temp = aux_station->info.temp;
									fav[i].humidity = aux_station->info.humidity;
									fav[i].wind_speed = aux_station->info.wind_speed;
									fav[i].wind_heading = aux_station->info.wind_heading;
									printf("\nStation added.\n");
									fprintf(arq, " Station added to favorites: %s \n", aux_station->info.name);
									break;
								}
								i++;                                  //percorre array até ao fim ate encontrar onde inserir, neste caso  array só permite ter 3 estaçoes como favoritas
							} while (i < 3);
						}
					}
					else { printf("\nStation does not exist.\n"); };
				}
			}
			else { printf("\nFavorites full !\n\n"); break; };
			gets(aux_str);
			break;

		case 11:
			aux_station = NULL;
			if (list.head != NULL) {
				while (aux_station == NULL) {
					printf(" \n Name Station?\n");
					scanf(" %[^\n]s", name);
					aux_station = search(&list, name);

					if (aux_station == NULL) {
						printf(" This station does not exist. Try another!\n");
					}
				}
				int COM = 3, baud = 9600;
				int i = 0;
				if (Serial_begin(COM, baud)) {
					for (i; i < 10; i++) {
						get_values(aux_station, COM, baud);
						printf(" Temperatur: %f ... Humidity: %f ... Wind speed: %f ... Wind heading: %f \n",
							aux_station->info.temp, aux_station->info.humidity, aux_station->info.wind_speed, aux_station->info.wind_heading);
					}
				}
				RS232_CloseComport(COM);
			}
			else printf("\n The list is empty!\n");
			gets(aux_str);
		}
	}
}