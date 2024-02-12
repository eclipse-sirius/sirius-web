/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useMutation } from '@apollo/client/react';
import {
  ServerContext,
  ServerContextValue,
  Toast,
  getCSSColor,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import { GQLButtonStyle, getTextDecorationLineValue } from '@eclipse-sirius/sirius-components-forms';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Grow from '@material-ui/core/Grow';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles, useTheme } from '@material-ui/core/styles';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import React, { useContext, useEffect, useRef, useState } from 'react';
import { addWidgetMutation } from './FormDescriptionEditorEventFragment';
import {
  GQLAddWidgetInput,
  GQLAddWidgetMutationData,
  GQLAddWidgetMutationVariables,
  GQLAddWidgetPayload,
  GQLErrorPayload,
} from './FormDescriptionEditorEventFragment.types';
import { SplitButtonWidgetState } from './SplitButtonWidget.type';
import { SplitButtonWidgetProps } from './WidgetEntry.types';
import { useFormDescriptionEditor } from './hooks/useFormDescriptionEditor';

const isErrorPayload = (payload: GQLAddWidgetPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const actionStyle = (theme: Theme, style: GQLButtonStyle): React.CSSProperties => {
  const actionStyle: React.CSSProperties = {
    backgroundColor: style.backgroundColor ? getCSSColor(style.backgroundColor, theme) : theme.palette.primary.light,
    color: style.foregroundColor ? getCSSColor(style.foregroundColor, theme) : 'white',
    fontSize: style.fontSize ? style.fontSize : undefined,
    fontStyle: style.italic ? 'italic' : undefined,
    fontWeight: style.bold ? 'bold' : undefined,
    textDecorationLine:
      style.underline || style.strikeThrough
        ? getTextDecorationLineValue(style.underline, style.strikeThrough)
        : undefined,
  };
  return actionStyle;
};

const imageStyle = (theme: Theme, iconOnly: boolean): React.CSSProperties => {
  return {
    marginRight: iconOnly ? theme.spacing(2) : theme.spacing(0),
    width: theme.spacing(2),
    height: theme.spacing(2),
  };
};

const useStyles = makeStyles<Theme>((theme) => ({
  bottomDropArea: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'whitesmoke',
    borderRadius: '10px',
    color: 'gray',
    height: '30px',
    width: '100%',
  },
  helpOutlineOutlined: {
    marginLeft: theme.spacing(1),
    fontSize: theme.typography.subtitle1.fontSize,
  },
  dragOver: {
    borderWidth: '1px',
    borderStyle: 'dashed',
    borderColor: theme.palette.primary.main,
  },
  selected: {
    color: theme.palette.primary.main,
  },
  containerAndLabel: {
    margin: '0px',
    padding: '0px',
    borderWidth: '1px',
    borderColor: 'gray',
    borderStyle: 'solid',
    borderRadius: '0px',
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

export const SplitButtonWidget = ({ widget }: SplitButtonWidgetProps) => {
  const { editingContextId, representationId, readOnly } = useFormDescriptionEditor();
  const noop = () => {};
  const initialState: SplitButtonWidgetState = {
    selected: false,
    open: false,
    selectedIndex: 0,
    message: '',
    actionsButtonLabel: [],
    actionsImageURL: [],
    actionsIsValidImage: [],
  };

  const [state, setState] = useState<SplitButtonWidgetState>(initialState);
  const theme: Theme = useTheme();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { selection } = useSelection();
  const buttonGroupRef = useRef<HTMLDivElement>(null);
  const widgetRef = useRef<HTMLDivElement>(null);

  const [addWidget, { loading: addWidgetLoading, data: addWidgetData, error: addWidgetError }] = useMutation<
    GQLAddWidgetMutationData,
    GQLAddWidgetMutationVariables
  >(addWidgetMutation);

  useEffect(() => {
    if (!addWidgetLoading) {
      if (addWidgetError) {
        setState((prevState) => {
          return { ...prevState, message: addWidgetError.message };
        });
      }
      if (addWidgetData) {
        const { addWidget } = addWidgetData;
        if (isErrorPayload(addWidget)) {
          setState((prevState) => {
            return { ...prevState, message: addWidget.message };
          });
        }
      }
    }
  }, [addWidgetLoading, addWidgetData, addWidgetError]);

  useEffect(() => {
    const actionsButtonLabel: string[] = [];
    const actionsImageURL: string[] = [];
    const actionsIsValidImage: boolean[] = [];

    widget.actions.forEach((action, index) => {
      actionsIsValidImage[index] = true;
      if (!action.imageURL) {
        actionsIsValidImage[index] = false;
      } else if (action.imageURL.startsWith('http://') || action.imageURL.startsWith('https://')) {
        actionsImageURL[index] = action.imageURL;
      } else {
        actionsImageURL[index] = httpOrigin + action.imageURL;
      }
      const buttonLabel: string = action.buttonLabel;
      const isButtonLabelBlank: boolean = buttonLabel == null || buttonLabel.trim() === '';
      if (actionsIsValidImage[index] && isButtonLabelBlank) {
        actionsButtonLabel[index] = null;
      } else if (!isButtonLabelBlank && !buttonLabel.startsWith('aql:')) {
        actionsButtonLabel[index] = buttonLabel;
      } else {
        actionsButtonLabel[index] = 'Lorem';
      }
    });

    setState((prevState) => {
      return {
        ...prevState,
        actionsButtonLabel: actionsButtonLabel,
        actionsImageURL: actionsImageURL,
        actionsIsValidImage: actionsIsValidImage,
        selectedIndex: state.selectedIndex >= widget.actions.length - 1 ? 0 : state.selectedIndex,
      };
    });
  }, [
    widget.actions.map((action) => action.imageURL).join(),
    widget.actions.map((action) => action.buttonLabel).join(),
  ]);

  useEffect(() => {
    if (widgetRef.current && selection.entries.find((entry) => entry.id === widget.id)) {
      widgetRef.current.focus();
      setState((prevState) => ({ ...prevState, selected: true }));
    } else {
      setState((prevState) => ({ ...prevState, selected: false }));
    }
  }, [selection, widget]);

  const classes = useStyles();

  const handleMenuItemClick = (_event, index) => {
    setState((prevState) => ({ ...prevState, open: false, selectedIndex: index }));
  };

  const handleToggle = () => {
    setState((prevState) => ({ ...prevState, open: !prevState.open }));
  };

  const handleClose = (event) => {
    if (widgetRef.current && widgetRef.current.contains(event.target)) {
      return;
    }
    setState((prevState) => ({ ...prevState, open: false }));
  };

  const handleDragEnter: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };

  const handleDragOver: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };

  const handleDragLeave: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);
  };

  const handleDrop: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);

    const id: string = event.dataTransfer.getData('draggedElementId');
    const type: string = event.dataTransfer.getData('draggedElementType');
    if (type !== 'Widget') {
      return;
    }

    if (id === 'Button') {
      const addWidgetInput: GQLAddWidgetInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        containerId: widget.id,
        kind: id,
        index: widget.actions.length || 0,
      };
      const addWidgetVariables: GQLAddWidgetMutationVariables = { input: addWidgetInput };
      addWidget({ variables: addWidgetVariables });
    }
  };

  const getStyle = (index: number): React.CSSProperties => {
    if (!widget.actions.at(index) || !widget.actions.at(index).style) {
      const defaultProps = {
        backgroundColor: null,
        foregroundColor: null,
        fontSize: theme.typography.subtitle2.fontSize,
        italic: null,
        bold: null,
        underline: null,
        strikeThrough: null,
      };
      return actionStyle(theme, defaultProps);
    }
    return actionStyle(theme, widget.actions[index].style);
  };

  return (
    <div className={classes.containerAndLabel} tabIndex={0} ref={widgetRef}>
      <div className={classes.propertySectionLabel}>
        <Typography variant="subtitle2" className={state.selected ? classes.selected : ''}>
          {widget.label}
        </Typography>
        {widget.hasHelpText ? <HelpOutlineOutlined color="secondary" className={classes.helpOutlineOutlined} /> : null}
      </div>
      <ButtonGroup
        variant="contained"
        color="primary"
        ref={buttonGroupRef}
        aria-label="split button"
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
        }>
        <Button
          data-testid={widget.label}
          variant="contained"
          color="primary"
          style={{ ...getStyle(state.selectedIndex) }}>
          {state.actionsIsValidImage[state.selectedIndex] && state.actionsImageURL[state.selectedIndex] ? (
            <img
              style={{ ...imageStyle(theme, state.actionsIsValidImage[state.selectedIndex]) }}
              alt={widget.actions[state.selectedIndex].label}
              src={state.actionsImageURL[state.selectedIndex]}
            />
          ) : null}
          {state.actionsButtonLabel[state.selectedIndex]}
        </Button>
        <Button
          color="primary"
          size="small"
          aria-controls={state.open ? 'split-button-menu' : undefined}
          aria-expanded={state.open ? 'true' : undefined}
          aria-label="select button action"
          aria-haspopup="menu"
          role={'show-actions'}
          onClick={handleToggle}
          style={{ ...getStyle(state.selectedIndex) }}>
          <ArrowDropDownIcon />
        </Button>
      </ButtonGroup>
      <Popper open={state.open} anchorEl={buttonGroupRef.current} transition placement="bottom">
        {({ TransitionProps, placement }) => (
          <Grow
            {...TransitionProps}
            style={{
              transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom',
            }}>
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList id="split-button-menu">
                  {widget.actions.map((option, index) => (
                    <MenuItem
                      key={index}
                      selected={index === state.selectedIndex}
                      onClick={(event) => handleMenuItemClick(event, index)}
                      style={{ ...getStyle(index) }}>
                      {state.actionsIsValidImage[index] && state.actionsImageURL[index] ? (
                        <img
                          style={{ ...imageStyle(theme, state.actionsIsValidImage[index]) }}
                          alt={option.label}
                          src={state.actionsImageURL[index]}
                        />
                      ) : null}
                      {state.actionsButtonLabel[index]}
                    </MenuItem>
                  ))}
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
      <div
        data-testid={`${widget.__typename}-Widgets-DropArea-${widget.id}`}
        className={classes.bottomDropArea}
        onDragEnter={readOnly ? noop : handleDragEnter}
        onDragOver={readOnly ? noop : handleDragOver}
        onDragLeave={readOnly ? noop : handleDragLeave}
        onDrop={readOnly ? noop : handleDrop}>
        <Typography variant="body1">{'Drag and drop a button widget here'}</Typography>
      </div>
      <Toast
        message={state.message}
        open={!!state.message}
        onClose={() =>
          setState((prevState) => {
            return { ...prevState, message: null };
          })
        }
      />
    </div>
  );
};
