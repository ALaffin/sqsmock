package io.findify.sqsmock.actions

import akka.actor.ActorSystem
import akka.event.slf4j.Logger
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import io.findify.sqsmock.config
import io.findify.sqsmock.messages.{ErrorResponse, ReceiveMessageResponse}
import io.findify.sqsmock.model.{Message, QueueCache}

import scala.collection.mutable

/**
  * Created by shutty on 3/29/16.
  */
class ReceiveMessageWorker(account:Long, queues:mutable.Map[String,QueueCache], system:ActorSystem) extends Worker {
  val log = Logger(this.getClass, "receive_message_worker")
  def process(fields:Map[String,String]) = {
    if(config.failReceive) {
      HttpResponse(StatusCodes.InternalServerError, entity = ErrorResponse("Receiver", "InternalServerError", "We encountered an internal error. Please try again.").toXML.toString())
    } else {
      val result = for (
        queueUrl <- fields.get("QueueUrl");
        queue <- queues.get(queueUrl)
      ) yield {
        val maxNumberOfMessages = fields.getOrElse("MaxNumberOfMessages", "1").toInt
        val messages = queue.dequeue(maxNumberOfMessages)
        log.debug(s"popping message from queue $queueUrl, messages: ${messages.size}")
        if(messages.size > 0) {
          log.info(s"sending message(s): $messages")
        }
        HttpResponse(StatusCodes.OK, entity = ReceiveMessageResponse(messages).toXML.toString())
      }
      result.getOrElse{
        log.warn("cannot send message: possibly, some request parameter is missing")
        HttpResponse(StatusCodes.BadRequest, entity = ErrorResponse("Sender", "InvalidParameterValue", "oops").toXML.toString())
      }
    }
  }
}
