package com.huiblog.huiblog.entity;

import lombok.*;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;

import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Indexed

@AnalyzerDef(name = "customanalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class)
        })
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "category_id")
    private String categoryID;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    //@Analyzer(definition = "customanalyzer")
    @Column(name = "title")
    private String title;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    //@Analyzer(definition = "customanalyzer")
    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private Date createdDate;

    @Column(name = "updated")
    private Date updatedDate;

    @Column(name = "img")
    private String img;

    @Column(name = "view_num")
    private int viewNum;
}
