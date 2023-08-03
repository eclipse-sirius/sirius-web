/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { ServerContext, ServerContextValue, getCSSColor, useSelection } from '@eclipse-sirius/sirius-components-core';
import { ButtonStyleProps, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { useContext, useEffect, useRef, useState } from 'react';
import { ButtonWidgetState } from './ButtonWidget.types';
import { ButtonWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme, ButtonStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) =>
      backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.light,
    color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : 'white'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    '&:hover': {
      backgroundColor: ({ backgroundColor }) =>
        backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.main,
      color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : 'white'),
      fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
      fontStyle: ({ italic }) => (italic ? 'italic' : null),
      fontWeight: ({ bold }) => (bold ? 'bold' : null),
      textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    },
  },
  icon: {
    marginRight: ({ iconOnly }) => (iconOnly ? theme.spacing(0) : theme.spacing(2)),
  },
  selected: {
    color: theme.palette.primary.main,
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

export const ButtonWidget = ({ widget }: ButtonWidgetProps) => {
  const initialState: ButtonWidgetState = {
    buttonLabel: widget.buttonLabel,
    imageURL: widget.imageURL,
    validImage: false,
    selected: false,
  };
  const [state, setState] = useState<ButtonWidgetState>(initialState);

  const props: ButtonStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
    iconOnly: state.buttonLabel ? false : true,
  };
  const classes = useStyles(props);

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { selection } = useSelection();

  const onErrorLoadingImage = () => {
    setState((prevState) => {
      return {
        ...prevState,
        validImage: false,
      };
    });
  };

  useEffect(() => {
    let newURL: string = null;
    let validURL: boolean = true;
    if (!widget.imageURL) {
      validURL = false;
    } else if (widget.imageURL.startsWith('http://') || widget.imageURL.startsWith('https://')) {
      newURL = widget.imageURL;
    } else {
      newURL = httpOrigin + widget.imageURL;
    }

    const buttonLabel: string = widget.buttonLabel;
    const isButtonLabelBlank: boolean = buttonLabel == null || buttonLabel.trim() === '';
    let newButtonLabel: string = null;
    if (validURL && isButtonLabelBlank) {
      newButtonLabel = null;
    } else if (!isButtonLabelBlank && !buttonLabel.startsWith('aql:')) {
      newButtonLabel = buttonLabel;
    } else {
      newButtonLabel = 'Lorem';
    }

    setState((prevState) => {
      return {
        ...prevState,
        buttonLabel: newButtonLabel,
        imageURL: newURL,
        validImage: validURL,
      };
    });
  }, [widget.imageURL, widget.buttonLabel]);

  const ref = useRef<HTMLButtonElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setState((prevState) => {
        return {
          ...prevState,
          selected: true,
        };
      });
    } else {
      setState((prevState) => {
        return {
          ...prevState,
          selected: false,
        };
      });
    }
  }, [selection, widget]);

  return (
    <div>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={state.selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} /> : null}
      </div>
      <Button
        data-testid={widget.label}
        classes={{ root: classes.style }}
        variant="contained"
        onFocus={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: true,
            };
          })
        }
        onBlur={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: false,
            };
          })
        }
        ref={ref}>
        {state.validImage && state.imageURL ? (
          <img
            className={classes.icon}
            width="16"
            height="16"
            alt={widget.label}
            src={state.imageURL}
            onError={onErrorLoadingImage}
          />
        ) : null}
        {state.buttonLabel}
      </Button>
    </div>
  );
};
