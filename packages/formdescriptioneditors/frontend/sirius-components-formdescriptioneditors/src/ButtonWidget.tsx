/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { ButtonStyleProps, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import Button from '@material-ui/core/Button';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useContext, useEffect, useRef, useState } from 'react';
import { ButtonWidgetState } from './ButtonWidget.types';
import { ButtonWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme, ButtonStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : theme.palette.primary.light),
    color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'white'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    '&:hover': {
      backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : theme.palette.primary.main),
      color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'white'),
      fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
      fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
      fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
      textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    },
  },
  icon: {
    marginRight: ({ iconOnly }) => (iconOnly ? theme.spacing(0) : theme.spacing(2)),
  },
  selected: {
    color: theme.palette.primary.main,
  },
}));

export const ButtonWidget = ({ widget, selection }: ButtonWidgetProps) => {
  const props: ButtonStyleProps = {
    backgroundColor: widget.style?.backgroundColor ?? null,
    foregroundColor: widget.style?.foregroundColor ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
    iconOnly: widget.buttonLabel ? false : true,
  };
  const classes = useStyles(props);

  const { httpOrigin }: ServerContextValue = useContext(ServerContext);
  const initialState: ButtonWidgetState = { imageURL: widget.imageURL, validImage: false, selected: false };
  const [state, setState] = useState<ButtonWidgetState>(initialState);

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
    let validURL = true;
    if (!widget.imageURL) {
      validURL = false;
    } else if (widget.imageURL.startsWith('http://') || widget.imageURL.startsWith('https://')) {
      newURL = widget.imageURL;
    } else {
      newURL = httpOrigin + widget.imageURL;
    }
    setState((prevState) => {
      return {
        ...prevState,
        imageURL: newURL,
        validImage: validURL,
      };
    });
  }, [widget.imageURL]);

  const ref = useRef<HTMLInputElement | null>(null);

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
      <Typography variant="subtitle2" className={state.selected ? classes.selected : ''}>
        {widget.label}
      </Typography>
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
        Lorem
      </Button>
    </div>
  );
};
