package org.dfpl.chronograph.chronoweb;

import java.net.Inet4Address;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.dfpl.chronograph.chronoweb.router.memory.StaticManipulationRouter;
import org.dfpl.chronograph.khronos.memory.manipulation.ChronoGraph;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Server extends AbstractVerticle {

	public static Logger logger;
	public static int port = 80;
	public ChronoGraph graph;

	public static Pattern resourcePattern = Pattern.compile(
			"(^[^\\|\\(\\)_]+$)|(^[^\\|\\(\\)_]+\\|[^\\|\\(\\)_]+\\|[^\\|\\(\\)_]+$)|(^[^\\|\\(\\)_]+_[0-9]+$)|(^[^\\|\\(\\)_]+_\\([0-9]+,[0-9]+\\)$)|(^[^\\|\\(\\)_]+\\|[^\\|\\(\\)_]+\\|[^\\|\\(\\)_]+_[0-9]+$)|(^[^\\|\\(\\)_]+\\|[^\\|\\(\\)_]+\\|[^\\|\\(\\)_]+_\\([0-9]+,[0-9]+\\)$)");
	public static Pattern vPattern = Pattern.compile("^[^\\|\\(\\)_]+$");
	public static Pattern ePattern = Pattern.compile("^[^\\|\\(\\)_]+\\|[^\\|\\(\\)_]+\\|[^\\|\\(\\)_]+$");

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		super.start(startPromise);

		final HttpServer server = vertx.createHttpServer();
		final Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());

		graph = new ChronoGraph();

		StaticManipulationRouter.registerAddElementRouter(router, graph);
		StaticManipulationRouter.registerGetElementRouter(router, graph);
		StaticManipulationRouter.registerGetElementsRouter(router, graph);
<<<<<<< HEAD
		StaticManipulationRouter.registerRemoveElementRouter(router, graph);
		StaticManipulationRouter.registerGetIncidentEdgesRouter(router, graph);
=======
		StaticManipulationRouter.registerGetIncidentEdgesRouter(router, graph);
		StaticManipulationRouter.registerGetAdjacentVerticesRouter(router, graph);
		StaticManipulationRouter.registerDeleteGraphRouter(router, graph);
>>>>>>> 00407e387c50c3f932bf1f9ac6529d6d27e43606

		server.requestHandler(router).listen(80);
		logger.info(
				"Chronoweb runs at http://" + Inet4Address.getLocalHost().getHostAddress() + ":" + port + "/chronoweb");
	}

	public static void setLogger() {
		Configurator.setRootLevel(Level.OFF);
		Configurator.setLevel(Server.class, Level.DEBUG);
		logger = LogManager.getLogger(Server.class);
	}

	public static void main(String[] args) {
		setLogger();
		Vertx.vertx().deployVerticle(new Server());
	}
}
