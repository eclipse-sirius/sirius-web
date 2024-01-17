/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { GQLStyledString, GQLStyledStringFragmentStyle, StyledLabelInputProps } from './StyledLabel.type';

export const splitText = (label: string, userInput: string | null): string[] => {
  if (!userInput) {
    return [label];
  }

  // Split the label in a case insensitive manner
  const caseInsensitiveSplitLabel: string[] = label
    .toLocaleLowerCase()
    .split(userInput.toLocaleLowerCase())
    .flatMap((value, index, array) => {
      if (index === 0 && value === '') {
        return [];
      } else if (index === array.length - 1 && value === '') {
        return [userInput.toLocaleLowerCase()];
      } else if (index === 0) {
        return [value];
      }
      return [userInput.toLocaleLowerCase(), value];
    });

  // Create the real result
  const splitLabel: string[] = [];
  let index = 0;
  for (const caseInsensitiveSegment of caseInsensitiveSplitLabel) {
    const caseSensitiveSegment = label.substring(index, index + caseInsensitiveSegment.length);
    splitLabel.push(caseSensitiveSegment);
    index = index + caseInsensitiveSegment.length;
  }

  return splitLabel;
};

const useTreeItemStyle = makeStyles((theme) => ({
  selected: {
    stroke: theme.palette.text.hint,
    fill: theme.palette.text.hint,
    backgroundColor: theme.palette.primary.main,
    '&:hover': {
      stroke: theme.palette.text.hint,
      fill: theme.palette.text.hint,
      backgroundColor: theme.palette.primary.main,
    },
  },
  label: {
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    maxWidth: '100ch',
  },
  marked: {
    fontWeight: 'bold',
  },
  selectedLabel: {
    fontWeight: 'bold',
  },
  highlight: {
    backgroundColor: theme.palette.navigation.leftBackground,
  },
}));

const getString = (styledString: GQLStyledString): string => {
  return styledString.styledStringFragments.map((fragments) => fragments.text).join();
};

const styledLabelStyle = (gqlLabelStyle: GQLStyledStringFragmentStyle): React.CSSProperties => {
  let textDecorations: String[] = [];
  if (gqlLabelStyle.underlineStyle) {
    textDecorations.push('underline');
    textDecorations.push(gqlLabelStyle.underlineStyle.toString());
    if (gqlLabelStyle.underlineColor) {
      textDecorations.push(gqlLabelStyle.underlineColor);
    }
  } else {
    textDecorations.push('none');
  }

  let border: String[] = [];
  if (gqlLabelStyle.borderStyle) {
    border.push(gqlLabelStyle.borderStyle.toString());
    if (gqlLabelStyle.borderColor) {
      border.push(gqlLabelStyle.borderColor);
    }
  } else {
    border.push('none');
  }

  const style: React.CSSProperties = {
    backgroundColor: gqlLabelStyle.backgroundColor,
    color: gqlLabelStyle.foregroundColor,
    textDecoration: textDecorations.join(' '),
    border: border.join(' '),
  };
  return style;
};

const styledLabelStyleStrikedout = (gqlLabelStyle: GQLStyledStringFragmentStyle): React.CSSProperties => {
  let textDecorations: String[] = [];
  if (gqlLabelStyle.isStrikedout) {
    textDecorations.push('line-through');
    if (gqlLabelStyle.strikeoutColor) {
      textDecorations.push(gqlLabelStyle.strikeoutColor);
    }
  }
  return {
    textDecoration: textDecorations.join(' '),
  };
};

const getStyledString = (styledString: GQLStyledString) => {
  return styledString.styledStringFragments.map((fragment, index) => {
    return (
      <span key={index} style={styledLabelStyle(fragment.styledStringFragmentStyle)}>
        <span style={styledLabelStyleStrikedout(fragment.styledStringFragmentStyle)}>{fragment.text}</span>
      </span>
    );
  });
};

export const StyledLabel = ({ styledString, selected, textToHighlight, marked }: StyledLabelInputProps) => {
  const classes = useTreeItemStyle();
  const textLabel = getString(styledString);
  let itemLabel: JSX.Element;
  const splitLabelWithTextToHighlight: string[] = splitText(textLabel, textToHighlight);
  if (
    textToHighlight === null ||
    textToHighlight === '' ||
    (splitLabelWithTextToHighlight.length === 1 &&
      splitLabelWithTextToHighlight[0] &&
      splitLabelWithTextToHighlight[0].toLocaleLowerCase() !== textLabel.toLocaleLowerCase())
  ) {
    itemLabel = <>{getStyledString(styledString)}</>;
  } else {
    const languages: string[] = Array.from(navigator.languages);
    itemLabel = (
      <>
        {splitLabelWithTextToHighlight.map((value, index) => {
          const shouldHighlight = value.localeCompare(textToHighlight, languages, { sensitivity: 'base' }) === 0;
          return (
            <span
              key={value + index}
              data-testid={`${textLabel}-${value}-${index}`}
              className={shouldHighlight ? classes.highlight : ''}>
              {value}
            </span>
          );
        })}
      </>
    );
  }

  return (
    <>
      <Typography
        variant="body2"
        className={`${classes.label} ${selected ? classes.selectedLabel : ''} ${marked ? classes.marked : ''}`}>
        {itemLabel}
      </Typography>
    </>
  );
};
