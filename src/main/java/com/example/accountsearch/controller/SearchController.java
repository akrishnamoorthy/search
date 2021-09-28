package com.example.accountsearch.controller;
import com.example.accountsearch.account.Account;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private Environment env;

        @GetMapping("/status")
        public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
            model.addAttribute("name", name);
            return "status";
        }

    @GetMapping("/search")
    public String search(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) throws SolrServerException, IOException {
        model.addAttribute("name", name);
        List<Account> accountList = getAccounts();
        model.addAttribute("accounts" , accountList);
        return "search";
    }

    public List<Account> getAccounts() throws SolrServerException, IOException {
        String solrUrl = env.getProperty("solr.url") != null ?  env.getProperty("solr.url")  : "http://localhost:8983/solr/accounts";
        String solrPwd = env.getProperty("solr.pwd") != null ?  env.getProperty("solr.pwd")  : "5qfOrZpA7u";;
        String solrUser = env.getProperty("solr.user") != null ?  env.getProperty("solr.user")  : "admin";;
        QueryRequest query = new QueryRequest(new SolrQuery("*:*"));
        query.setBasicAuthCredentials(solrUser, solrPwd);
        SolrClient client = new HttpSolrClient.Builder(solrUrl).build();
        QueryResponse rsp = query.process(client);
        final SolrDocumentList documents = rsp.getResults();
        List<Account> accountList = new ArrayList<>();
        System.out.println("Found " + documents.getNumFound() + " documents");
        for(SolrDocument document : documents) {
            final String id = (String) document.getFirstValue("accountId");
            final String name = (String) document.getFirstValue("accountName");
            final String address = (String) document.getFirstValue("address");
            accountList.add(new Account(id,name,address));
        }
       return accountList;
    }
}

