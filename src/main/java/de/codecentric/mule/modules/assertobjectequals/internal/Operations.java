package de.codecentric.mule.modules.assertobjectequals.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultComparisonFormatter;
import org.xmlunit.diff.Diff;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class Operations {

  /**
   * Compare two objects. Drill down into {@link Map} and {@link List}, use {@link Object#equals(Object)} for all other
   * classes.
   *
   * @param expected
   *            The expected value. Automatic conversions are provided:
   *            <ul>
   *            <li>InputStream is read/parsed as Json</li>
   *            <li>byte[] is parsed as Json</li>
   *            <li>String is parsed as Json when it starts with [ or { (after <code>trim()</code></li>
   *            </ul>
   *            Remember: Encoding for Json is always UTF8
   *
   * @param actual
   *            The actual value. Automatic conversions are provided:
   *            <ul>
   *            <li>InputStream is read/parsed as Json</li>
   *            <li>byte[] is parsed as Json</li>
   *            <li>String is parsed as Json when it starts with [ or { (after <code>trim()</code></li>
   *            </ul>
   *            Remember: Encoding for Json is always UTF8
   *
   * @param containsOnlyOnMaps
   *            The actual value entry set of maps can contain more values than the expected set. So you tests do not fail
   *            when there are more elements than expected in the result.
   *
   * @param checkMapOrder
   *            The order of map entries is checked. The default is to ignore order of map entries.
   *
   * @param pathOptions
   *            Options for path patterns to control the comparison. Syntax of one List entry: Zero to <code>n</code> path
   *            parts. The parts can have the following syntax:
   *            <ul>
   *            <li><code>?</code>: Wildcard one, matches one element in a path</li>
   *            <li><code>*</code>: Wildcard any, matches zero to <code>n</code> elements in a path</li>
   *            <li><code>[#]</code>: List wildcard, matches a list entry with any index</li>
   *            <li><code>[0]</code>: Matches a list entry with the given number. 0 or positive numbers: Count from
   *            beginning, negative number: Cound from end (-1 is last element)</li>
   *            <li><code>['.*']</code>: Matches a map entry where the key must match the given regular expression. If you
   *            need a ' in the expression, just write ''. The example '.*' matches all keys.</li>
   *            </ul>
   *            A space as separator. One or more of the following options (case not relevant):
   *
   *            CONTAINS_ONLY_ON_MAPS: The actual value entry set of maps can contain more values than the expected set. So you tests do not fail
   *            when there are more elements than expected in the result.
   *
   *            CHECK_MAP_ORDER: The order of map entries is checked. The default is to ignore order of map entries.
   *
   *            IGNORE: The actual node and its subtree is ignored completely.
   *
   * @throws Exception
   *             When comparison fails or on technical problems (e.g. parsing)
   */
  public void compareObjects(
          @DisplayName("Expected value") @Example("#[MunitTools::getResourceAsStream()]") Object expected,
          @DisplayName("Actual value") @Example("#[payload]") Object actual,
          @DisplayName("Contains only on maps") @Optional(defaultValue = "false") boolean containsOnlyOnMaps,
          @DisplayName("Check map order") @Optional(defaultValue = "false") boolean checkMapOrder,
          @DisplayName("Path patterns+options") List<String> pathOptions)

          throws Exception {

      Object expectedObj = convert2Object(expected);
      Object actualObj = convert2Object(actual);
      ObjectComparator comparator = createComparator(containsOnlyOnMaps, checkMapOrder, pathOptions == null ? new ArrayList<String>() : pathOptions);
      Collection<String> diff = comparator.compare(expectedObj, actualObj);

      if (!diff.isEmpty()) {
          StringBuilder messageBuilder = new StringBuilder();
          for (String s : diff) {
              if (messageBuilder.length() > 0) {
                messageBuilder.append(System.lineSeparator());
              }
              messageBuilder.append(s);
          }
          throw new AssertionError(messageBuilder);
      }
  }

  /**
   * Compare two XML documents. See <a href="https://github.com/xmlunit/user-guide/wiki/">XMLUnit Wiki</a>} how this works
   *
   * @param expected
   *            The expected value, XML as String, InputStream, byte[] or DOM tree.
   * @param actual
   *            The actual value, XML as String, InputStream, byte[] or DOM tree.
   * @param xmlCompareOption
   *            How to compare the XML documents.
   *
   *            IGNORE_COMMENTS: Will remove all comment-Tags "<!-- Comment -->" from test- and control-XML before
   *            comparing.
   *
   *            IGNORE_WHITESPACE: Ignore whitespace by removing all empty text nodes and trimming the non-empty ones.
   *
   *            NORMALIZE_WHITESPACE: Normalize Text-Elements by removing all empty text nodes and normalizing the
   *            non-empty ones.
   *
   * @throws Exception
   *             When comparison fails or on technical problems (e.g. parsing)
   */
  public void compareXml(
          @DisplayName("Expected value") @Example("#[MunitTools::getResourceAsStream()]") Object expected,
          @DisplayName("Actual value") @Example("#[payload]") Object actual,
          @DisplayName("XML compare option") @OfValues(XmlCompareOption.class) String xmlCompareOption)

          throws Exception {

      DiffBuilder diffBuilder = DiffBuilder.compare(expected).withTest(actual);

      switch (xmlCompareOption) {
          case "IGNORE_COMMENTS":
              diffBuilder = diffBuilder.ignoreComments();
              break;
          case "IGNORE_WHITESPACE":
              diffBuilder = diffBuilder.ignoreWhitespace();
              break;
          case "NORMALIZE_WHITESPACE":
              diffBuilder = diffBuilder.normalizeWhitespace();
              break;
          default:
              throw new IllegalArgumentException("I forgot to implement for a new enum constant.");
      }

      Diff diff = diffBuilder.build();

      if (diff.hasDifferences()) {
          throw new AssertionError(diff.toString(new DefaultComparisonFormatter()));
      }
  }

  private Object convert2Object(Object value) throws JsonProcessingException, IOException {
      if (value == null) {
          return null;
      } else if (value instanceof InputStream) {
          return new ObjectMapper().readerFor(Object.class).readValue((InputStream) value);
      } else if (value instanceof byte[]) {
          return new ObjectMapper().readerFor(Object.class).readValue((byte[]) value);
      } else if (value instanceof CharSequence) {
          String trimmed = ((CharSequence) value).toString().trim();
          if (trimmed.startsWith("[") || trimmed.startsWith("{")) {
              return new ObjectMapper().readerFor(Object.class).readValue(trimmed);
          } else {
              return value;
          }
      } else {
          return value;
      }
  }

  private ObjectComparator createComparator(boolean containsOnlyOnMaps, boolean checkMapOrder, List<String> pathOptionsStrings) {
      PathPatternParser ppp = new PathPatternParser();
      Collection<PathPattern> patterns = new ArrayList<>();

      for (String pathOptionString : pathOptionsStrings) {
          patterns.add(ppp.parse(pathOptionString));
      }
      EnumSet<PathOption> rootOptions = EnumSet.noneOf(PathOption.class);
      if (containsOnlyOnMaps) {
          rootOptions.add(PathOption.CONTAINS_ONLY_ON_MAPS);
      }
      if (checkMapOrder) {
          rootOptions.add(PathOption.CHECK_MAP_ORDER);
      }

      ObjectCompareOptionsFactory optionFactory = new PatternBasedOptionsFactory(rootOptions, patterns);
      return new ObjectComparator(optionFactory);
    }
}
