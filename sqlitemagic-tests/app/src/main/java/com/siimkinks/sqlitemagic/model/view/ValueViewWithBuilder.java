package com.siimkinks.sqlitemagic.model.view;

import com.google.auto.value.AutoValue;
import com.siimkinks.sqlitemagic.CompiledSelect;
import com.siimkinks.sqlitemagic.Select;
import com.siimkinks.sqlitemagic.annotation.View;
import com.siimkinks.sqlitemagic.annotation.ViewColumn;
import com.siimkinks.sqlitemagic.annotation.ViewQuery;
import com.siimkinks.sqlitemagic.model.immutable.SimpleValueWithBuilder;
import com.siimkinks.sqlitemagic.model.immutable.SimpleValueWithCreator;

import static com.siimkinks.sqlitemagic.AuthorTable.AUTHOR;
import static com.siimkinks.sqlitemagic.MagazineTable.MAGAZINE;
import static com.siimkinks.sqlitemagic.SimpleValueWithBuilderTable.SIMPLE_VALUE_WITH_BUILDER;
import static com.siimkinks.sqlitemagic.SimpleValueWithCreatorTable.SIMPLE_VALUE_WITH_CREATOR;

@View
@AutoValue
public abstract class ValueViewWithBuilder {
  @ViewQuery
  static final CompiledSelect QUERY = Select
      .columns(
          SIMPLE_VALUE_WITH_BUILDER.all(),
          MAGAZINE.NAME.as("mn"),
          SIMPLE_VALUE_WITH_CREATOR.all(),
          AUTHOR.NAME
      )
      .from(MAGAZINE)
      .join(AUTHOR)
      .join(SIMPLE_VALUE_WITH_BUILDER)
      .join(SIMPLE_VALUE_WITH_CREATOR)
      .where(MAGAZINE.AUTHOR.is(AUTHOR.ID))
      .queryDeep()
      .compile();

  @ViewColumn("mn")
  public abstract String magazineName();

  @ViewColumn("author.name")
  public abstract String authorName();

  @ViewColumn("simple_value_with_builder")
  public abstract SimpleValueWithBuilder simpleBuilder();

  @ViewColumn("simple_value_with_creator")
  public abstract SimpleValueWithCreator simpleCreator();

  public static Builder builder() {
    return new AutoValue_ValueViewWithBuilder.Builder();
  }

  @AutoValue.Builder
  public static abstract class Builder {

    public abstract Builder magazineName(String magazineName);

    public abstract Builder authorName(String authorName);

    public abstract Builder simpleBuilder(SimpleValueWithBuilder simpleBuilder);

    public abstract Builder simpleCreator(SimpleValueWithCreator simpleCreator);

    public abstract ValueViewWithBuilder build();
  }
}