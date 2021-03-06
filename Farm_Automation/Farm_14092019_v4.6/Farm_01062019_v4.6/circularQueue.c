/*
 * circularQueue.c
 *
 * Created: 9/23/2019 1:29:35 PM
 *  Author: aelnabty
 */ 
#include "circularQueue.h"

uint8_t gBuffer[MAX_BUFFER_LENGTH];
uint8_t head = 0u;// for dequeue
uint8_t tail = 0u;// for enqueue

uint8_t circularQueue_isEmpty()
{
	uint8_t ret = FALSE;
	
	if(head == tail)
		{
			ret = TRUE;
		}
	else
	{
		ret = FALSE;
	}
	
	return ret;
	
}
void circularQueue_enqueue(uint8_t data)
{
	
	if(tail == MAX_BUFFER_LENGTH)
	{
		tail = 0;
	}
	gBuffer[tail] = data;
	tail = tail + 1;
	
	
}
uint8_t circularQueue_dequeue()
{
	uint8_t ret;
	
	if(head == MAX_BUFFER_LENGTH)
	{
		head = 0;
	}
	
	ret = gBuffer[head];
	
	head = head +1;
	
	return ret;
	
}

void circularQueue_clearBuffer()
{
	int i=0;
	for (i=0;i<MAX_BUFFER_LENGTH;i++)
	{
		gBuffer[i]=0;
	}
	tail = 0;
	head = 0;
	
}
uint8_t circularQueue_searchSubStringInQueue(uint8_t* data)
{
	uint8_t ret = FALSE;
	if( strstr(gBuffer,data) == NULL)
	{
		ret = FALSE;
	}
	else
	{
		ret = TRUE;
	}
	
	return ret;
}

uint8_t *circularQueue_bufferPointer()
{
	return gBuffer;
} 