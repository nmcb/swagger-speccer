package net.zalando.speccer

import org.parboiled2._

class Yaml(val input: ParserInput) extends Parser {

    def InputLine = rule { c_printable ~ EOI }

    // escaped characters
    def c_escape                   = c_backslash
    def ns_esc_null                = CharPredicate('0')
    def ns_esc_bell                = CharPredicate('a')
    def ns_esc_backspace           = CharPredicate('b')
    def ns_esc_horizontal_tab      = CharPredicate('t') ++ CharPredicate('\u0009')
    def ns_esc_line_feed           = CharPredicate('n')
    def ns_esc_vertical_tab        = CharPredicate('v')
    def ns_esc_form_feed           = CharPredicate('f')
    def ns_esc_carriage_return     = CharPredicate('r')
    def ns_esc_escape              = CharPredicate('e')
    def ns_esc_space               = CharPredicate('\u0020')
    def ns_esc_double_quote        = CharPredicate('\"')
    def ns_esc_slash               = CharPredicate('/')
    def ns_esc_backslash           = c_backslash
    def ns_esc_next_line           = CharPredicate('N')
    def ns_esc_non_breaking_space  = CharPredicate('_')
    def ns_esc_line_seperator      = CharPredicate('L')
    def ns_esc_paragraph_seperator = CharPredicate('P')
    def ns_esc_8_bit               = rule { CharPredicate('x') ~ ns_hex_digit ~ ns_hex_digit }
    def ns_esc_16_bit              = rule { CharPredicate('u') ~ ns_hex_digit ~ ns_hex_digit ~ ns_hex_digit ~ ns_hex_digit }
    def ns_esc_32_bit              = rule { CharPredicate('U') ~ ns_hex_digit ~ ns_hex_digit ~ ns_hex_digit ~ ns_hex_digit ~ ns_hex_digit ~ ns_hex_digit ~ ns_hex_digit ~ ns_hex_digit }

    def ns_esc_char                = c_backslash ~ (
      ns_esc_null | ns_esc_bell | ns_esc_backspace | ns_esc_horizontal_tab | ns_esc_line_feed | ns_esc_vertical_tab |
      ns_esc_form_feed | ns_esc_carriage_return | ns_esc_escape | ns_esc_space | ns_esc_double_quote | ns_esc_slash |
      ns_esc_backslash | ns_esc_next_line | ns_esc_non_breaking_space | ns_esc_line_seperator | ns_esc_paragraph_seperator |
      ns_esc_8_bit | ns_esc_16_bit | ns_esc_32_bit
    )

    // miscellaneous characters
    def ns_dec_digit    = CharPredicate.Digit
    def ns_hex_digit    = CharPredicate.HexDigit
    def ns_ascii_letter = CharPredicate.Alpha
    def ns_word_char    = ns_dec_digit ++ ns_ascii_letter ++ c_dash
    def ns_uri_char     = rule { (c_percentage ++ ns_hex_digit ++ ns_hex_digit ) -- ns_word_char | anyOf("#;/?:@&=+$,_.!~*'()[]") }
    def ns_tag_char     = rule { ns_uri_char ~ !c_tag ~ !c_flow_indicator }

    // white space characters
    def s_tab   = c_tab
    def s_space = c_space
    def s_white = c_tab ++ c_space
    def ns_char = nb_char -- s_white

    // line break characters
    def b_char            = b_line_feed ++ b_carriage_return
    def b_line_feed       = c_line_feed
    def b_carriage_return = c_carriage_return
    def nb_char           = c_printable -- ( b_char ++ c_byte_order_mark )
    def b_break           = rule { b_carriage_return ~ b_line_feed | b_carriage_return | b_line_feed }
    def b_as_line_feed    = b_break
    def b_non_content     = b_break

    // indicators
    def c_indicator = c_sequence_entry ++ c_mapping_key ++ c_mapping_value ++ c_collect_value ++ c_sequence_start ++
                    c_sequence_end ++ c_mapping_start ++ c_mapping_end ++ c_comment ++ c_anchor ++ c_alias ++
                    c_tag ++ c_literal ++ c_block ++ c_single_quote ++ c_double_quote ++ c_directive ++ c_reserved

    def c_flow_indicator = c_collect_value ++ c_sequence_start ++ c_sequence_end ++ c_mapping_start ++ c_mapping_end

    def c_sequence_entry = CharPredicate('-')
    def c_mapping_key    = CharPredicate('?')
    def c_mapping_value  = CharPredicate(':')
    def c_collect_value  = CharPredicate(',')
    def c_sequence_start = CharPredicate('[')
    def c_sequence_end   = CharPredicate(']')
    def c_mapping_start  = CharPredicate('{')
    def c_mapping_end    = CharPredicate('}')
    def c_comment        = CharPredicate('#')
    def c_anchor         = CharPredicate('&')
    def c_alias          = CharPredicate('*')
    def c_tag            = CharPredicate('!')
    def c_literal        = CharPredicate('|')
    def c_block          = CharPredicate('>')
    def c_single_quote   = CharPredicate('\'')
    def c_double_quote   = CharPredicate('\"')
    def c_directive      = c_percentage
    def c_reserved       = CharPredicate('@') ++ CharPredicate('`')

    // allowed characters
    def c_printable = c_tab ++ b_line_feed ++ b_carriage_return ++ c_ascii ++ c_nel ++ c_utf_excl_ctrl
    def nb_json     = c_tab ++ c_utf_excl_break


    // character sets
    def c_tab             = CharPredicate('\t')
    def c_space           = CharPredicate(' ')
    def c_line_feed       = CharPredicate('\n')
    def c_carriage_return = CharPredicate('\r')
    def c_dash            = CharPredicate('-')
    def c_backslash       = CharPredicate('\\')
    def c_percentage      = CharPredicate('%')
    def c_ascii           = CharPredicate('\u0020' to '\u007E')
    def c_nel             = CharPredicate('\u0085')
    def c_utf_excl_ctrl   = CharPredicate('\u00A0' to '\uD7FF') ++ CharPredicate('\uE000' to '\uFFFD')
    def c_utf_excl_break  = CharPredicate('\u0020' to '\uFFFD')
    def c_byte_order_mark = CharPredicate('\uFEFF')
}