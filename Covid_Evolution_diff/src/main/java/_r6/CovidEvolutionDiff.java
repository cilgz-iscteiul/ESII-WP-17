package _r6;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

/** Retira de um repositorio no github, as 2 versoes mais recentes do ficheiro covid19spreading.rdf
 *  que tem tags associadas , e gera uma pagina html com os dois ficheiros lado a lado
 * @author Miguel Mira
 *
 */
public class CovidEvolutionDiff {
	

	/**
	 * file1 Ficheiro
	 */
	private static String file1=null;
	/**
	 * file2 Ficheiro
	 */
	private static String file2=null;

	

	/** Clona o repositorio dado num repositorio local
	 * 
	 */
	public static void clones() throws InvalidRemoteException, TransportException, GitAPIException {
		BasicConfigurator.configure();
		Git.cloneRepository()
		.setURI("https://github.com/vbasto-iscte/ESII1920.git")
		.setDirectory(new File("ES2_requisito6"))
		.call();
	}
	
	/** obtem as 2 versoes mais recentes do ficheiro covid19spreading.rdf
	 *  que tem tags associadas e armazena cada um dos ficheiros duma String(file1,file2)
	 * 
	 */
	public static void getLastVersions() throws IOException {
		FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
		Repository repository = repositoryBuilder.setGitDir(new File("ES2_requisito6/.git"))
				.readEnvironment() 
				.findGitDir() 
				.setMustExist(true)
				.build();
		Git.open(new File("ES2_requisito6"))
		.checkout();
		ObjectId lastCommitId = repository.resolve("HEAD~4");
		ObjectId lastCommitId1 = repository.resolve("HEAD~2");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();

		try (RevWalk revWalk = new RevWalk(repository)) {
			RevCommit commit = revWalk.parseCommit(lastCommitId);
			RevCommit commit1 = revWalk.parseCommit(lastCommitId1);
			RevTree tree = commit.getTree();
			RevTree tree1 = commit1.getTree();
			try (TreeWalk treeWalk = new TreeWalk(repository)) {
				treeWalk.addTree(tree);
				treeWalk.setRecursive(true);
				treeWalk.setFilter(PathFilter.create("covid19spreading.rdf"));
				if (!treeWalk.next()) {
					throw new IllegalStateException("covid19spreading.rdf'");
				}
				ObjectId objectId = treeWalk.getObjectId(0);
				ObjectLoader loader = repository.open(objectId);
				loader.copyTo(baos);
				file1=new String(baos.toByteArray(), "UTF-8");

			}
			try (TreeWalk treeWalk2 = new TreeWalk(repository)) {
				treeWalk2.addTree(tree1);
				treeWalk2.setRecursive(true);
				treeWalk2.setFilter(PathFilter.create("covid19spreading.rdf"));
				if (!treeWalk2.next()) {
					throw new IllegalStateException("covid19spreading.rdf'");
				}
				ObjectId objectId = treeWalk2.getObjectId(0);
				ObjectLoader loader1 = repository.open(objectId);
				loader1.copyTo(baos1);
				file2=new String(baos1.toByteArray(), "UTF-8");
			}
			revWalk.dispose();
		}
	}
	
	/** Constroi uma pagina HTML com os dois ficheiros lado a lado,
	 * ambos dentro de uma area de texto
	 */
	public static void buildHTML() throws IOException {
		File f = new File("CovidEvolutionDiff.htm");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write("<html><body><h1>CovidEvolutionDiff</h1>");
		bw.write("<textarea cols=90 rows=200>");
		bw.write(file1);
		bw.write("</textarea>");
		bw.write("<textarea cols=90 rows=200>");
		bw.write(file2);
		bw.write("</textarea>");
		bw.write("</body></html>");
		bw.close();
		Desktop.getDesktop().browse(f.toURI());
		System.out.println(Desktop.getDesktop());
		
	}

	public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		clones();
		getLastVersions();
		buildHTML();
	}

}
