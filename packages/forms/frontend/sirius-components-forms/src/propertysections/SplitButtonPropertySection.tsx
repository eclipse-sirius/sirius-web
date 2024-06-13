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
import { useMutation } from '@apollo/client';
import { ServerContext, ServerContextValue, getCSSColor, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import Grow from '@material-ui/core/Grow';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import { Theme, makeStyles } from '@material-ui/core/styles';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import gql from 'graphql-tag';
import { useContext, useEffect, useRef, useState } from 'react';
import { GQLButton } from '../form/FormEventFragments.types';
import {
  ButtonStyleProps,
  GQLErrorPayload,
  GQLPushButtonInput,
  GQLPushButtonMutationData,
  GQLPushButtonMutationVariables,
  GQLPushButtonPayload,
  GQLSuccessPayload,
} from './ButtonPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusPayload,
  SplitButtonPropertySectionProps,
  SplitButtonState,
} from './SplitButtonPropertySection.types';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';

const useStyle = makeStyles<Theme, ButtonStyleProps>((theme) => ({
  style: {
    backgroundColor: ({ backgroundColor }) =>
      backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.light,
    color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : 'white'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
    fontStyle: ({ italic }) => (italic ? 'italic' : null),
    fontWeight: ({ bold }) => (bold ? 'bold' : null),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    paddingTop: theme.spacing(0.5),
    paddingBottom: theme.spacing(0.5),
    '&:hover': {
      backgroundColor: ({ backgroundColor }) =>
        backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.main,
      color: ({ foregroundColor }) => (foregroundColor ? getCSSColor(foregroundColor, theme) : 'white'),
      fontSize: ({ fontSize }) => (fontSize ? fontSize : null),
      fontStyle: ({ italic }) => (italic ? 'italic' : null),
      fontWeight: ({ bold }) => (bold ? 'bold' : null),
      textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    },
    '&.Mui-disabled': {
      color: theme.palette.action.disabled,
      backgroundColor: theme.palette.action.disabledBackground,
      opacity: 1,
    },
  },
  icon: {
    marginRight: ({ iconOnly }) => (iconOnly ? theme.spacing(0) : theme.spacing(2)),
    height: theme.spacing(2),
    width: theme.spacing(2),
  },
}));

const useContainerStyle = makeStyles<Theme>((theme) => ({
  style: {
    display: 'flex',
    flexDirection: 'row',
    gap: theme.spacing(2),
  },
}));

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const pushButtonMutation = gql`
  mutation pushButton($input: PushButtonInput!) {
    pushButton(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLPushButtonPayload | GQLUpdateWidgetFocusPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLPushButtonPayload | GQLUpdateWidgetFocusPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const SplitButtonPropertySection = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: SplitButtonPropertySectionProps) => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [state, setState] = useState<SplitButtonState>({
    open: false,
    selectedIndex: 0,
  });

  const buttonGroupRef = useRef<HTMLDivElement>(null);

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  if (widget.actions.length == 0) {
    return null;
  }

  const firstEnabledAction = widget.actions.find((action) => !action.readOnly);

  useEffect(() => {
    if (!!firstEnabledAction) {
      setState((prevState) => ({ ...prevState, selectedIndex: widget.actions.indexOf(firstEnabledAction) }));
    }
  }, []);

  const classes = widget.actions.map((action) => {
    const props: ButtonStyleProps = {
      backgroundColor: action.style?.backgroundColor ?? null,
      foregroundColor: action.style?.foregroundColor ?? null,
      fontSize: action.style?.fontSize ?? null,
      italic: action.style?.italic ?? null,
      bold: action.style?.bold ?? null,
      underline: action.style?.underline ?? null,
      strikeThrough: action.style?.strikeThrough ?? null,
      iconOnly: action.buttonLabel ? false : true,
    };
    return useStyle(props);
  });

  const containerClasses = useContainerStyle();

  const [pushButton, { loading, data, error }] = useMutation<GQLPushButtonMutationData, GQLPushButtonMutationVariables>(
    pushButtonMutation
  );

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { pushButton } = data;
        if (isErrorPayload(pushButton) || isSuccessPayload(pushButton)) {
          addMessages(pushButton.messages);
        }
      }
    }
  }, [loading, error, data]);

  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData, GQLUpdateWidgetFocusMutationVariables>(updateWidgetFocusMutation);

  const sendUpdateWidgetFocus = (selected: boolean) => {
    const input: GQLUpdateWidgetFocusInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      widgetId: widget.id,
      selected,
    };
    const variables: GQLUpdateWidgetFocusMutationVariables = {
      input,
    };
    updateWidgetFocus({ variables });
  };

  useEffect(() => {
    if (!updateWidgetFocusLoading) {
      if (updateWidgetFocusError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          addMessages(updateWidgetFocus.messages);
        }
      }
    }
  }, [updateWidgetFocusLoading, updateWidgetFocusData, updateWidgetFocusError]);

  const onFocus = () => sendUpdateWidgetFocus(true);
  const onBlur = () => {
    sendUpdateWidgetFocus(false);
  };

  const handleClick = () => {
    const button: GQLButton = widget.actions[state.selectedIndex];
    const input: GQLPushButtonInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      buttonId: button.id,
    };
    const variables: GQLPushButtonMutationVariables = { input };
    pushButton({ variables });
  };

  const handleMenuItemClick = (_event, index) => {
    setState((prevState) => ({ ...prevState, open: false, selectedIndex: index }));
  };

  const handleToggle = () => {
    setState((prevState) => ({ ...prevState, open: !prevState.open }));
  };

  const handleClose = (event) => {
    if (buttonGroupRef.current && buttonGroupRef.current.contains(event.target)) {
      return;
    }
    setState((prevState) => ({ ...prevState, open: false }));
  };

  return (
    <div className={containerClasses.style}>
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        data-testid={widget.label}
      />
      <ButtonGroup
        variant="contained"
        color="primary"
        ref={buttonGroupRef}
        aria-label="split button"
        onFocus={onFocus}
        onBlur={onBlur}>
        <Button
          data-testid={widget.label}
          variant="contained"
          color="primary"
          onClick={handleClick}
          disabled={readOnly || widget.readOnly || widget.actions[state.selectedIndex].readOnly}
          classes={{ root: classes[state.selectedIndex].style }}>
          {widget.actions[state.selectedIndex].imageURL?.length > 0 ? (
            <img
              className={classes[state.selectedIndex].icon}
              alt={widget.actions[state.selectedIndex].label}
              src={httpOrigin + widget.actions[state.selectedIndex].imageURL}
            />
          ) : null}
          {widget.actions[state.selectedIndex].buttonLabel}
        </Button>
        <Button
          color="primary"
          size="small"
          aria-controls={state.open ? 'split-button-menu' : undefined}
          aria-expanded={state.open ? 'true' : undefined}
          aria-label="select button action"
          aria-haspopup="menu"
          role={'show-actions'}
          disabled={readOnly || widget.readOnly}
          onClick={handleToggle}
          classes={{ root: classes[state.selectedIndex].style }}>
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
                      classes={{ root: classes[index].style }}
                      disabled={readOnly || widget.readOnly || widget.actions[index].readOnly}>
                      {option.imageURL?.length > 0 ? (
                        <img className={classes[index].icon} alt={option.label} src={httpOrigin + option.imageURL} />
                      ) : null}
                      {option.buttonLabel}
                    </MenuItem>
                  ))}
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
    </div>
  );
};
